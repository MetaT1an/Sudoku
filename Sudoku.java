package sudoku;

import java.util.Arrays;
/*
this class is designed to generate one solution of Sudo,
to mask several numbers at random in all 81 numbers to raise a Sudo problem,
to define the rules to check whether the numbers filled by user are correct
 */
public class Sudoku{
    private int[][] solution;

    //initialize the sudoku according to
    //-the regularity of the sum of i and j
    public Sudoku(){
        solution = new int[9][];

        for(int i = 0;i<9;i++){
            solution[i] = new int[9];
            for(int j = 1;j<=9;++j)
                this.solution[i][j-1] = (i+j>9)?(i+j-9):i+j;
        }
        martrix();
        colSwap();
        mask();
    }

    //return a concrete and random solution
    public int getDigit(int i,int j){

        return solution[i][j];
    }

    //construct a situation object to the sudoku rules
    //by extracting the line[147,258,369] from the solution
    private void martrix(){

        int[][] temp = new int[9][9];
        for(int i = 0,j = 0;i<3;i++){
            temp[j++] = Arrays.copyOf(solution[i],solution[i].length);
            temp[j++] = Arrays.copyOf(solution[i+3],solution[i+3].length);
            temp[j++] = Arrays.copyOf(solution[i+6],solution[i+6].length);
        }
        solution = temp;
    }

    //based on the[123,456,789] column group
    //swap each column value among each group
    private void colSwap(){
        int colForSwap= 0;
        int[] temp = new int[9];

        for(int i = 0;i<9;i++){
            switch(i){  //choose the column to swap
                case 0:
                case 1:
                case 2: colForSwap = (int)(Math.random()*3); break;//group1
                case 3:
                case 4:
                case 5: colForSwap = (int)(Math.random()*3 + 3); break;//group2
                case 6:
                case 7:
                case 8: colForSwap = (int)(Math.random()*3 + 6);//group3
            }
            if(colForSwap!=i)
                for(int j = 0;j<9;j++){ //j for line
                    temp[j] = solution[j][colForSwap];   //the same column,diffent line
                    solution[j][colForSwap] = solution[j][i];
                    solution[j][i] = temp[j];
                }//finish one-column swap
        }//finish all-columns swap
    }

    private void mask(){
    //choose the value masked at random
        for(int i = 0;i<9;++i){
            for(int j = 0;j<9;j++){
                int difficulty = (int)(Math.random()*2);    //0 or 1
                if(difficulty==0)
                    solution[i][j] = 0;
            }
        }
    }

    private boolean lineVlid(int i,int j,int value){
        for(int m = 0;m<9 && m != j;m++)
            if(solution[i][m] == value)
                return false;
        return true;
    }

    private boolean colValid(int i,int j,int value){
        for(int n = 0;n<9 && n!=i;n++)
            if(solution[n][j] == value)
                return false;
        return true;
    }
    private boolean recValid(int i,int j,int value){
        for(int m = 3*(i/3);m<=3*(i/3)+2;++m)
            for(int n = 3*(j/3);n<3*(j/3)+2;++n)
                if(i!=m && j!=n && solution[m][n]==value)
                    return false;
        return true;
    }

    public boolean isValid(int i,int j,int value){
        if(value>0 && value<10)
            return (lineVlid(i,j,value)&&colValid(i,j,value)&&recValid(i,j,value));
        else
            return false;
    }
}

