
package manish_zappos_application;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import manish_zappos_application.Products;

class Item implements Comparable<Item>{         //implements comparable to support ordering based on the 'diff' value
	int start;
	int end;
	int diff;

	public Item(int start, int end, int diff) {         //public constuctor to store start index, end index and diff between the user entered sum and calculated sum
		super();
		this.start = start;
		this.end = end;
		this.diff = diff;
	}

	@Override
	public String toString() {
		return "Start="+start + " End=" + end + " Diff=" + diff;
	}

	@Override
	public int compareTo(Item o) {
		return this.diff - o.diff;          //compare two product objects based on diff
	}
}


public class ProductCombos {
    
    public ProductCombos() {
    }
            
    ArrayList<Products> Product_List = new ArrayList<Products>();
    public ProductCombos(ArrayList<Products> Product_List)
    {
        this.Product_List = Product_List;               //constructor to get the list of products
    }
    
	PriorityQueue<Item> list = new PriorityQueue<>();       //pripority queue to store the objects based on difference between the sum values 

	int subArraySum(int sum, int no_of_products) {
                
                int n = Product_List.size();
		float curr_sum = Product_List.get(0).price;     //get first product price from the list
                int start = 0, i;
		boolean ans = false;
		int s = 0, e = 0;
		int minDiff = Integer.MAX_VALUE;
                boolean flag = false;

		for (i = 1; i <= n; i++) {
                        
			while (curr_sum > sum && start < i - 1) {           //when the current sum value exceeds the user sum value, we keep on removing price of earlier proucts, till the sum becomes equal or less
				curr_sum = curr_sum - Product_List.get(start).price;
				start++;
                                
			}
                        
                       
                            
                        
			if ((sum - curr_sum) >= 0 && (sum - curr_sum) <= minDiff) {
				if (no_of_products == (((i - 1) - start) + 1)) {    //check if number of products matching the sum is same as teh user entered product count
                                    
                                    minDiff = (int)(sum - curr_sum);
					s = start;
					e = i - 1;
					list.add(new Item(s, e, minDiff));      //add products to priority queue
					ans = true;
                                        if(minDiff == 0)
                                            flag = true;
				}

			}

			if (i < n) 
				curr_sum = curr_sum + Product_List.get(i).price;        //keep on finding the combination of products till the final prdouct is reached
                                
		}

                int diff = 0;
                if(list.peek() != null)
                {
		diff = list.peek().diff;    //get the product combination whose difference with the user sum is the least

		while (!list.isEmpty()) {
			Item item = list.poll();
			if (item.diff == diff) {        //gets other product combination only if it is equal to minimum difference
                          //  int count = 0;
                            
                                if(flag == false) {
                                    //System.out.println("THE ENTERED SUM IS TOO LARGE..PLEASE NARROW YOUR SEARCH.." + no_of_products + " PRODUCTS ARE AVAILABLE FOR AS LOW AS $" + (sum - diff));
                                    return (sum - diff);
                                }
                                
                                else
                                {
                                System.out.println("The following combination of products are available at $" + (sum - diff));
                                for(int x = item.start; x <= item.end; x++) {
                                //System.out.println("Combination " + count + " is listed below");
                                System.out.println();    
                                System.out.println(Product_List.get(x).productName);
                                System.out.println(Product_List.get(x).brandName);
                                System.out.println(Product_List.get(x).styleId);
                                System.out.println(Product_List.get(x).price);
                                System.out.println("----------------------------------------");
                               
                                }
                                System.out.println();
                                System.out.println();
                                System.out.println();
                                }
                                
			}
		}
		//return ans;
        }
               // else
                 //   return false;
                return (sum - diff);
	}

    
}
