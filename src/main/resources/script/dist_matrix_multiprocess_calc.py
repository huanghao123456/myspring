import numpy as np
import matplotlib.pylab as plt
from Bio.PDB import *
from multiprocessing import Pool, cpu_count
import io
import base64
import sys
import warnings
warnings.filterwarnings("ignore")


def minimal_residue_dist(residue_one, residue_two):
    """计算两者的最小距离"""
    minimal_distance = 25
    for atom_one in residue_one:
        for atom_two in residue_two:
            diff_vector = atom_one.get_vector() - atom_two.get_vector()
            dist = np.sqrt(np.sum(diff_vector * diff_vector))
            if dist > 25:  # far enough
                return 25
            elif dist < minimal_distance:
                minimal_distance = dist
    return minimal_distance


def calc_dist_matrix_row(args):
    """计算形如 a1 func [b1, b2, b3, b4]的任务"""
    row, residue_one, chain_two = args
    return [minimal_residue_dist(residue_one, residue_two) for residue_two in chain_two]


def calc_dist_matrix(chain_one, chain_two, num_processes=None):
    """
    将形如 [a1, a2, a3, a4] func [b1, b2, b3, b4]的运算改为
    a1 func [b1, b2, b3, b4]
    a2 func [b1, b2, b3, b4]
    a3 func [b1, b2, b3, b4]
    a4 func [b1, b2, b3, b4]
    这些是待提交的任务，每个任务对应一个进程
    """
    args = [(row, residue_one, chain_two) for row, residue_one in enumerate(chain_one)]
    if num_processes is None:
        # 进程池大小设为CPU核心数的一半，防止CPU过载而告警
        num_processes = cpu_count() // 2
    with Pool(processes=num_processes) as pool:
        results = pool.map(calc_dist_matrix_row, args)
    return np.array(results)


if __name__ == '__main__':
    parser = PDBParser()
    pdb_id = sys.argv[1]
    pdb_path = sys.argv[2]
    structures = parser.get_structure(pdb_id, pdb_path)
    chain = structures[0]["A"]
    dist_matrix = calc_dist_matrix(chain, chain)
    plt.imshow(dist_matrix)
    buffer = io.BytesIO()
    plt.savefig(buffer, format='png')
    buffer.seek(0)
    image_base64 = base64.b64encode(buffer.getvalue()).decode('utf-8')
    print(image_base64)

    # base64_bytes = base64.b64decode(image_base64)
    # from PIL import Image
    # image = Image.open(io.BytesIO(base64_bytes))
    # image.show()

    ppb = PPBuilder()
    pp = ppb.build_peptides(structures[0]["A"])[0]
    pdb_seq = pp.get_sequence()
    print(pdb_seq)
