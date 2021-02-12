/*=============================================================================
| Assignment: HW 02 – Calculating the 8, 16, or 32 bit checksum for a
| given input file
|
| Author: Connor Cabrera
| Language: C
|
| To Compile: gcc -o checksum checksum.c -lm
|
| To Execute: ./checksum textfile.txt checksum_size
| where the files in the command line are in the current directory.
|
| The text file contains text is mixed case with spaces, punctuation,
| and is terminated by the hexadecimal ‘0A’, inclusive.
| (The 0x’0A’ is included in the checksum calculation.)
|
| The checksum_size contains digit(s) expressing the checksum size
| of either 8, 16, or 32 bits
 |
| Class: CIS3360 - Security in Computing - Fall 2020
| Instructor: McAlpin
| Due Date: 11/22/2020
|
+=============================================================================*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>

int main(int argc, char **argv){

    if(argc == 1){
        printf("Too few arguments");
        exit(0);
    }

    FILE *input;

    input = fopen(argv[1], "r");
    int checksumSize = atoi(argv[2]);

    if(checksumSize != 8 && checksumSize != 16 && checksumSize != 32){
        fprintf(stderr, "Valid checksum sizes are 8, 16, or 32\n");
        exit(0);
    }

    printf("\n");

    int plaintext[10000] = {0};
    char currentChar;
    int letterCount = 0;

    fscanf(input, "%c", &currentChar);
    plaintext[letterCount] = currentChar;
    printf("%c", plaintext[letterCount]);
    letterCount++;

    while(!feof(input)){
        fscanf(input, "%c", &currentChar);
        if(currentChar != '\n'){
            plaintext[letterCount] = currentChar;
            printf("%c", plaintext[letterCount]);
            letterCount++;
            if(letterCount == 80){
                printf("\n");
            }
        }
    }

    plaintext[letterCount] = '\n';
    printf("%c", plaintext[letterCount]);
    letterCount++;

    if(checksumSize == 16 && letterCount %2 == 1){
        plaintext[letterCount] = 'X';
        printf("%c", plaintext[letterCount]);
        letterCount++;
    }
    if(checksumSize == 32 && letterCount % 4 != 0){
        int pad = 4 - (letterCount % 4);
        for(int i = 0; i < pad; i++){
            plaintext[letterCount] = 'X';
            printf("%c", plaintext[letterCount]);
            letterCount++;
        }
    }
    printf("\n");

    unsigned long checksum;
    int reverse = 0;

    if(checksumSize == 8){

        int binaryCurrent[8] = {0};
        int binaryAdd[8] = {0};
        int binaryCarry[8] = {0};
        int start = plaintext[0];
        int add = plaintext[1];
        int i = 7;
        int j = 7;

        while(start > 0){
            binaryCurrent[i] = start % 2;
            start = start / 2;
            i--;
        }

        for(int x = 1; x < letterCount; x++){

            while(add > 0){
                binaryAdd[j] = add % 2;
                add = add / 2;
                j--;
            }

            for(int i = 7; i >= 0; i--){

                if(binaryCurrent[i] == 1 && binaryAdd[i] == 1 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 1 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 1;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 0 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 1 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 0 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 1;
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 0 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 1;
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 1 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 1;
                }
                else{
                    binaryCurrent[i] = 0;
                }
            }
            for(int y = 0; y < 8; y++){
                binaryCarry[y] = 0;
            }
            for(int y = 0; y < 8; y++){
                binaryAdd[y] = 0;
            }

            if(x < letterCount - 1){
                add = plaintext[x + 1];
            }
            j = 7;
        }

        for(int i = checksumSize - 1; i >= 0; i--){
            checksum += (binaryCurrent[i] * pow(2, reverse));
            reverse++;
        }
    }

    if(checksumSize == 16){
        int binaryCurrent[16] = {0};
        int binaryAdd[16] = {0};
        int binaryCarry[16] = {0};

        int start1 = plaintext[0];
        int start2 = plaintext[1];
        int add1 = plaintext[2];
        int add2 = plaintext[3];
        int i = 15;
        int j = 15;

        while(start2 > 0){
            binaryCurrent[i] = start2 % 2;
            start2 = start2 / 2;
            i--;
        }
        i = 7;
        while(start1 > 0){
            binaryCurrent[i] = start1 % 2;
            start1 = start1 / 2;
            i--;
        }

        for(int x = 2; x < letterCount; x += 2){
            while(add2 > 0){
                binaryAdd[j] = add2 % 2;
                add2 = add2 / 2;
                j--;
            }
            j = 7;
            while(add1 > 0){
                binaryAdd[j] = add1 % 2;
                add1 = add1 / 2;
                j--;
            }

            for(int i = 15; i >= 0; i--){

                if(binaryCurrent[i] == 1 && binaryAdd[i] == 1 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 1 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 1;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 0 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 1 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 0 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 1;
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 0 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 1;
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 1 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 1;
                }
                else{
                    binaryCurrent[i] = 0;
                }
            }
            for(int y = 0; y < 16; y++){
                binaryCarry[y] = 0;
            }
            for(int y = 0; y < 16; y++){
                binaryAdd[y] = 0;
            }

            if(x < letterCount - 2){
                add1 = plaintext[x + 2];
                add2 = plaintext[x + 3];
            }
            j = 15;
        }

        for(int i = checksumSize - 1; i >= 0; i--){
            checksum += (binaryCurrent[i] * pow(2, reverse));
            reverse++;
        }
    }

    if(checksumSize == 32){
        int binaryCurrent[32] = {0};
        int binaryAdd[32] = {0};
        int binaryCarry[32] = {0};

        int start1 = plaintext[0];
        int start2 = plaintext[1];
        int start3 = plaintext[2];
        int start4 = plaintext[3];
        int add1 = plaintext[4];
        int add2 = plaintext[5];
        int add3 = plaintext[6];
        int add4 = plaintext[7];
        int i = 31;
        int j = 31;

        while(start4 > 0){
            binaryCurrent[i] = start4 % 2;
            start4 = start4 / 2;
            i--;
        }
        i = 23;
        while(start3 > 0){
            binaryCurrent[i] = start3 % 2;
            start3 = start3 / 2;
            i--;
        }
        i = 15;
        while(start2 > 0){
            binaryCurrent[i] = start2 % 2;
            start2 = start2 / 2;
            i--;
        }
        i = 7;
        while(start1 > 0){
            binaryCurrent[i] = start1 % 2;
            start1 = start1 / 2;
            i--;
        }

        for(int x = 4; x < letterCount; x += 4){
            while(add4 > 0){

                binaryAdd[j] = add4 % 2;
                add4 = add4 / 2;
                j--;
            }
            j = 23;
            while(add3 > 0){

                binaryAdd[j] = add3 % 2;
                add3 = add3 / 2;
                j--;
            }
            j = 15;
            while(add2 > 0){
                binaryAdd[j] = add2 % 2;
                add2 = add2 / 2;
                j--;
            }
            j = 7;
            while(add1 > 0){
                binaryAdd[j] = add1 % 2;
                add1 = add1 / 2;
                j--;
            }

            for(int i = 31; i >= 0; i--){

                if(binaryCurrent[i] == 1 && binaryAdd[i] == 1 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 1 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 1;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 0 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 1 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 0;
                    if(i > 0){
                        binaryCarry[i - 1] = 1;
                    }
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 0 && binaryCarry[i] == 1){
                    binaryCurrent[i] = 1;
                }
                else if(binaryCurrent[i] == 1 && binaryAdd[i] == 0 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 1;
                }
                else if(binaryCurrent[i] == 0 && binaryAdd[i] == 1 && binaryCarry[i] == 0){
                    binaryCurrent[i] = 1;
                }
                else{
                    binaryCurrent[i] = 0;
                }
            }
            for(int y = 0; y < 32; y++){
                binaryCarry[y] = 0;
            }
            for(int y = 0; y < 32; y++){
                binaryAdd[y] = 0;
            }

            if(x < letterCount - 4){
                add1 = plaintext[x + 4];
                add2 = plaintext[x + 5];
                add3 = plaintext[x + 6];
                add4 = plaintext[x + 7];
            }
            j = 31;
        }

        for(int i = checksumSize - 1; i >= 0; i--){
            checksum += (binaryCurrent[i] * pow(2, reverse));
            reverse++;
        }
    }

    printf("%2d bit checksum is %8lx for all %4d chars\n", checksumSize, checksum, letterCount);

    fclose(input);
}



/*=============================================================================
| I Connor Cabrera (co472243) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
