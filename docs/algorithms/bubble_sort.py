# -*- coding: utf-8 -*-
"""
Title     : Bubble Sorting
Objective : Show each step of comparing and movement in intuitive way
Created by: Mara
Created on: 2018/3/18 17:33
"""
import random

ODATA = []
DATA_LENGTH = 10;

def bubble_sort_push_end():
    DATA = ODATA.copy()
    compare_count = 0
    move_count = 0;
    print("\n** bubble_sort_push_end")
    print("** This algorithm always pushes the largest data to end.")
    print("DATA:   " + str(DATA))
    for i in range(0, DATA_LENGTH-1):
        print("i=" + str(i))
        moved = 0
        for j in range(0, DATA_LENGTH - i - 1):
            compare_count = compare_count + 1
            print("  j=" + str(j) + "  comparing:'" + str( DATA[j]) + ''" and '"+ str( DATA[j+1]) + "'")
            if DATA[j] > DATA[j+1]:
                tmp1 = DATA[j]
                tmp2 = DATA[j+1]
                DATA[j+1] = tmp1
                DATA[j] =  tmp2
                move_count = move_count + 1
                print("     exchanging:'" + str( DATA[j]) + "' and "''+ str( DATA[j+1]) + "'")
                print("     DATA: [", end="")
                for m in range(0, DATA_LENGTH):
                    if m == j or m == j+1:
                        print('\033[1;34;m', end="")
                        print(str(DATA[m]), end="")
                        if m != (DATA_LENGTH - 1):
                            print(", ", end="")
                        print("\033[0m", end="")
                    elif m > DATA_LENGTH - i - 1:
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
                moved = 1
        print("  DATA: [", end="")
        for m in range(0, DATA_LENGTH):
            if m >= DATA_LENGTH - i - 1:
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
        if moved == 0:
            print("Need not move again and already in order. End sorting now.")
            break
    return compare_count, move_count

def bubble_sort_pop_front():
    DATA = ODATA.copy()
    compare_count = 0
    move_count = 0;
    print("\n** bubble_sort_pop_front")
    print("** This algorithm always pops the smallest data to front.")
    print("DATA:   " + str(DATA))
    for i in range(0, DATA_LENGTH-1):
        print("i=" + str(i))
        moved = 0
        for j in range(DATA_LENGTH-1, i, -1):
            compare_count = compare_count + 1
            print("  j=" + str(j) + "  comparing:'" + str( DATA[j]) + "' and '"+ str( DATA[j-1])+ "'")
            if DATA[j] < DATA[j-1]:
                tmp1 = DATA[j]
                tmp2 = DATA[j-1]
                DATA[j-1] = tmp1
                DATA[j] =  tmp2
                move_count = move_count + 1
                print("     exchanging:'" + str( DATA[j]) + ''" and '"+ str( DATA[j-1]) + "'")
                print("     DATA: [", end="")
                for m in range(0, DATA_LENGTH):
                    if m == j or m == j-1:
                        print('\033[1;34;m', end="")
                        print(str(DATA[m]), end="")
                        if m != (DATA_LENGTH - 1):
                            print(", ", end="")
                        print("\033[0m", end="")
                    elif m < i:
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
                moved = 1
        print("  DATA: [", end="")
        for m in range(0, DATA_LENGTH):
            if m <= i:
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
        if moved == 0:
            print("Need not move again and already in order. End sorting now.")
            break
    return compare_count, move_count

def generate_int_list(number):
    ODATA = []
    while len(ODATA) < number:
        data = random.randint(0, number-1)
        if data not in ODATA:
            ODATA.append(data)
    print("\nDATA:   " + str(ODATA))
    return ODATA

if __name__ == '__main__':
    ODATA = generate_int_list(DATA_LENGTH)

    compare_count, move_count = bubble_sort_push_end()
    print("## Data size: " + str(DATA_LENGTH))
    print("## Total compared: " + str(compare_count))
    print("## Total moved: " + str(move_count))


    compare_count, move_count = bubble_sort_pop_front()
    print("## Data size: " + str(DATA_LENGTH))
    print("## Total compared: " + str(compare_count))
    print("## Total moved: " + str(move_count))




