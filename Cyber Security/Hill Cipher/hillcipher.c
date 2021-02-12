/*=============================================================================
| Assignment: HW 01 – Encrypting a plaintext file using the Hill cipher in the key file
|
| Author: Connor Cabrera
| Language: C
|
| To Compile: gcc -o hillcipher hillcipher.c
|
| To Execute:   ./hillcipher hillcipherkey.txt plaintextfile.txt
|               where the files in the command line are in the current directory.
|               The key text contains a single digit on the first line defining the size of the key
|               while the remaining lines define the key, for example:
|               3
|               1 2 3
|               4 5 6
|               7 8 9
|               The plain text file contains the plain text in mixed case with spaces & punctuation.
|
| Class: CIS3360 - Security in Computing - Fall 2020
| Instructor: McAlpin
| Due Date: 10/11/2020
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

    FILE *key;
    FILE *plaintext;
    key = fopen(argv[1], "r");
    plaintext = fopen(argv[2], "r");

    int matrixSize;
    int keyNum;

    fscanf(key, "%d", &matrixSize);

    if(matrixSize < 2){
        printf("Key Matrix too small (<2x2)");
        exit(0);
    }

    char alphabet[26] = "abcdefghijklmnopqrstuvwxyz";

    printf("\n\nKey matrix:\n\n");
    char keyMatrix[matrixSize][matrixSize];

    for(int i = 0; i < matrixSize; i++){
        for(int j = 0; j < matrixSize; j++){
            fscanf(key, "%d", &keyNum);
            printf("%d ", keyNum);
            keyMatrix[i][j] = keyNum;
        }
        printf("\n");
    }
    printf("\n\n");
    printf("Plaintext:\n\n");

    char ptLetter;
    fscanf(plaintext, "%c", &ptLetter);
    int letToNum = ptLetter;
    int letterCount = 0;
    int neatness = 0;
    char encryptMe[10000];

    while(!feof(plaintext)){

        if(letToNum > 64 && letToNum < 91){
            ptLetter += 32;
            letToNum += 32;
        }

        if(letToNum > 96 && letToNum < 123){
            printf("%c", ptLetter);
            encryptMe[letterCount] = ptLetter;
            fscanf(plaintext, "%c", &ptLetter);
            letToNum = ptLetter;
            letterCount++;
            neatness++;
            if(neatness == 80){
                printf("\n");
                neatness = 0;
            }
        }

        else{
            fscanf(plaintext, "%c", &ptLetter);
            letToNum = ptLetter;
        }
    }

    neatness = 0;

    int pad = letterCount % matrixSize;
    if(pad != 0){
        pad = (matrixSize - (letterCount % matrixSize));
        for(int x = 0; x < pad; x++){
            printf("x");
            encryptMe[letterCount + x] = 'x';
        }
    }

    int arraySize = letterCount + pad;

    printf("\n\n\n");
    neatness = 0;
    int hillRows = arraySize / matrixSize;
    char hillCipher[hillRows][matrixSize];
    for(int a = 0; a < arraySize; a++){
        hillCipher[a / matrixSize][a % matrixSize] = encryptMe[a];
    }

    printf("Ciphertext:\n\n");
    int encryptedChar = 0;
    for(int b = 0; b < hillRows; b++){
        for(int c = 0; c < matrixSize; c++){
            for(int d = 0; d < matrixSize; d++){
                encryptedChar += (keyMatrix[c][d] * (hillCipher[b][d] - 97));
            }
        printf("%c", alphabet[encryptedChar % 26]);
        neatness++;
        if(neatness == 80){
                printf("\n");
                neatness = 0;
        }
        encryptedChar = 0;
        }
    }
    printf("\n");
}

/*=============================================================================
| I Connor Cabrera (co472243) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
