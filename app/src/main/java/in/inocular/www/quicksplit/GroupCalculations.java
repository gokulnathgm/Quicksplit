package in.inocular.www.quicksplit;

/**
 * Created by anil on 12/11/15.
 */
public class GroupCalculations {

    public int[][] addExpense(int[][] expense) {

        int numberOfMembers = expense.length, lP, lN;
        int balances[][] = new int[numberOfMembers][numberOfMembers];
        int i, temporary[] = new int[numberOfMembers];
        int owings[] = new int[numberOfMembers];

        owings = getOwings(expense);


		/* ------------- Add the values in owings with the values in db n update the db -----------*/
		/* ------------- Copy the new values into a temporary array and pass it into method below ----------*/

        balances = getTransactionsFromNetOwings(owings);


		/*
		for(int l=0;l<numberOfMembers;l++) {
			for(int gg=0;gg<numberOfMembers;gg++)
				System.out.print("\t" + balances[l][gg] + " ");
			System.out.println();
		}*/

        return balances;
    }

    public int[] getOwings(int[][] expense) {
        int[] owings = new int[expense.length];
        // calculates the net amount owed by each person in the group
        for(int i=0;i<expense.length;i++) {
            owings[i] = expense[i][0] - expense[i][1];
        }
        return owings;
    }

    public int[][] getTransactionsFromNetOwings(int[] temporary) {

        int lP,lN;
        int numberOfMembers = temporary.length;
        int balances[][] = new int[numberOfMembers][numberOfMembers];

        // repeats untill everyone has payed or received the net owings
        while(notSettledUp(temporary)) {
            // finds the richest and poorest of the group
            lP = largestPositive(temporary);
            lN = largestNegative(temporary);

            // A transaction is made between them.
            // The one with less owings among them, will be settled
            if( temporary[lP]>Math.abs(temporary[lN]) ) {
                balances[lN][lP] = temporary[lN];
                balances[lP][lN] = 0 - temporary[lN];
                temporary[lP] += temporary[lN];
                temporary[lN] = 0;
            } else {
                balances[lN][lP] = 0 - temporary[lP];
                balances[lP][lN] = temporary[lP];
                temporary[lN] += temporary[lP];
                temporary[lP] = 0;
            }
        }
        return balances;
    }

    boolean notSettledUp(int[] array) {
        // check whether everyone owes only zero
        int i;
        for(i=0;i<array.length;i++)
            if(array[i] != 0 )
                return true;
        return false;
    }

    int largestPositive(int[] array) {
        // returns the richest member
        int i,lPos = 0;
        for(i=1;i<array.length;i++) {
            if(array[i] > array[lPos])
                lPos = i;
        }
        return lPos;
    }

    int largestNegative(int[] array) {
        //returns the poorest member
        int i,sPos = 0;
        for(i=1;i<array.length;i++) {
            if(array[i] < array[sPos])
                sPos = i;
        }
        return sPos;
    }

}