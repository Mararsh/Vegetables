# -*- coding: utf-8 -*-
"""
Title     : Selection Sorting
Objective : Show each step of comparing and movement in intuitive way
Created by: Mara
Created on: 2018/3/19 21:01
"""


import random

ODATA = []
DATA_LENGTH = 20

def generate_data(number):
    while len(ODATA) < number:
        data = random.randint(0, number-1)
        if data not in ODATA:
            ODATA.append(data)
    print("\nDATA:   " + str(ODATA))


def selection_sort_find_max():
    DATA = ODATA.copy()
    compare_count = 0
    move_count = 0;
    print("\n** selection_sort_find_max")
    print("** This algorithm always finds the largest data and move it to end.")
    print("DATA:   " + str(DATA))
    for i in range(0, DATA_LENGTH):
        current_index = DATA_LENGTH - 1 - i
        print("i=" + str(current_index))
        max_index = current_index
        for j in range(0, DATA_LENGTH - 1 - i):
            compare_count = compare_count + 1
            print("  j=" + str(j) + "  comparing:'" + str( DATA[j]) + "' and '"+ str( DATA[max_index]) + "'")
            if DATA[j] > DATA[max_index]:
                max_index = j
                print("     max=" + str(DATA[max_index]) )
        if max_index == current_index:
            continue
        tmp1 = DATA[max_index]
        tmp2 = DATA[current_index]
        DATA[max_index] = tmp2
        DATA[current_index] = tmp1
        move_count = move_count + 1
        print("     exchanging:'" + str( DATA[max_index]) + "' and '"+ str( DATA[current_index])  + "'")
        print("     DATA: [", end="")
        for m in range(0, DATA_LENGTH):
            if m==current_index:
                print('\033[1;33;40m', end="")
                print(str(DATA[m]), end="")
                if m!=(DATA_LENGTH-1):
                    print(", ", end="")
                print("\033[0m", end="")
            elif m == max_index:
                print('\033[1;33;m', end="")
                print(str(DATA[m]), end="")
                if m != (DATA_LENGTH - 1):
                    print(", ", end="")
                print("\033[0m", end="")
            elif m >= DATA_LENGTH - 1 - i:
                print('\033[1;32;40m', end="")
                print(str(DATA[m]), end="")
                if m != (DATA_LENGTH - 1):
                    print(", ", end="")
                print("\033[0m", end="")
            else:
                print(str(DATA[m]), end="")
                if m!=(DATA_LENGTH-1):
                    print(", ", end="")
        print("]")
    return compare_count, move_count


def selection_sort_find_min():
    DATA = ODATA.copy()
    compare_count = 0
    move_count = 0;
    print("\n** selection_sort_find_min")
    print("** This algorithm always finds the smallest data and move it to front.")
    print("DATA:   " + str(DATA))
    for i in range(0, DATA_LENGTH):
        current_index = i
        print("i=" + str(current_index))
        min_index = current_index
        for j in range(i+1, DATA_LENGTH):
            compare_count = compare_count + 1
            print("  j=" + str(j) + "  comparing:'" + str(DATA[j]) + "' and '" + str(DATA[min_index]) + "'")
            if DATA[j] < DATA[min_index]:
                min_index = j
                print("     min=" + str(DATA[min_index]))
        if min_index == current_index:
            continue
        tmp1 = DATA[min_index]
        tmp2 = DATA[current_index]
        DATA[min_index] = tmp2
        DATA[current_index] = tmp1
        move_count = move_count + 1
        print("     exchanging:'" + str(DATA[min_index]) + "' and '" + str(DATA[current_index]) + "'")
        print("     DATA: [", end="")
        for m in range(0, DATA_LENGTH):
            if m==current_index:
                print('\033[1;33;40m', end="")
                print(str(DATA[m]), end="")
                if m!=(DATA_LENGTH-1):
                    print(", ", end="")
                print("\033[0m", end="")
            elif m == min_index:
                print('\033[1;33;m', end="")
                print(str(DATA[m]), end="")
                if m != (DATA_LENGTH - 1):
                    print(", ", end="")
                print("\033[0m", end="")
            elif m <= i:
                print('\033[1;32;40m', end="")
                print(str(DATA[m]), end="")
                if m != (DATA_LENGTH - 1):
                    print(", ", end="")
                print("\033[0m", end="")
            else:
                print(str(DATA[m]), end="")
                if m != (DATA_LENGTH - 1):
                    print(", ", end="")
        print("]")
    return compare_count, move_count


if __name__ == '__main__':
    generate_data(DATA_LENGTH)

    compare_count, move_count = selection_sort_find_max()
    print("## Data size: " + str(DATA_LENGTH))
    print("## Total compared: " + str(compare_count))
    print("## Total moved: " + str(move_count))

    compare_count, move_count = selection_sort_find_min()
    print("## Data size: " + str(DATA_LENGTH))
    print("## Total compared: " + str(compare_count))
    print("## Total moved: " + str(move_count))





