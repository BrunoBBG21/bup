package flatten;


import java.util.ArrayList;
import java.util.List;

import flatten.Tree.Node;

public class MyFlattenTree<T> implements FlattenTree<T> {
/*
 * Implement a method that, given a tree as a parameter, will return an inorder traversal of that tree.

Your implementation should throw an IllegalArgumentException if the tree is null.

Your implementation must implement the FlattenTree interface
would result in the list [1,5,4,9,6]. 
  /|\
 1 | 6
  /|\
 5 4 9
 * 
 * 
   /|\
  1 | 6
   /|\
  5 4 |
 	 /|\
    8 9 7
    
    
   /|\
  1 | 6
   /|\
  5 4 |
 	 /|\
    | 9 7
   /|\
  8 2 3
  
 * 
 */
	
	
	public static void main(String[] args) {
		Tree<Long> tree = new Tree.Node(Tree.Leaf.leaf(1), Tree.Node.tree(Tree.Leaf.leaf(5), Tree.Leaf.leaf(4), Tree.Leaf.leaf(9)), Tree.Leaf.leaf(6));
		System.out.println(new MyFlattenTree<Long>().flattenInOrder(tree));
		
		tree = new Tree.Node(Tree.Leaf.leaf(1), 
							Tree.Node.tree(Tree.Leaf.leaf(5), 
											Tree.Leaf.leaf(4), 
											Tree.Node.tree(Tree.Leaf.leaf(8), 
															Tree.Leaf.leaf(9), 
															Tree.Leaf.leaf(7))), 
							Tree.Leaf.leaf(6));
		System.out.println(new MyFlattenTree<Long>().flattenInOrder(tree));
		
		tree = new Tree.Node(Tree.Leaf.leaf(1), 
							Tree.Node.tree(Tree.Leaf.leaf(5), 
											Tree.Leaf.leaf(4), 
											Tree.Node.tree(Tree.Node.tree(Tree.Leaf.leaf(8), 
																			Tree.Leaf.leaf(2), 
																			Tree.Leaf.leaf(3)), 
															Tree.Leaf.leaf(9), 
															Tree.Leaf.leaf(7))), 
							Tree.Leaf.leaf(6));
		System.out.println(new MyFlattenTree<Long>().flattenInOrder(tree));
	}
	
	Function<T, T> funcLeft = new Function<T, T>() {
		@Override
		public T apply(T p) {
//			System.out.println("funcLeft : " + p);
			return p;
		}
	};
	
	Function<Triple<Tree<T>>, List<T>> funcRigth = new Function<Triple<Tree<T>>, List<T>>() {
		@Override
		public List<T> apply(Triple<Tree<T>> p) {
			List<T> value = new ArrayList<T>();
//			System.out.println("funcRigth : " + p);
			value.addAll(new MyFlattenTree<T>().flattenInOrder(p.left()));
			value.addAll(new MyFlattenTree<T>().flattenInOrder(p.middle()));
			value.addAll(new MyFlattenTree<T>().flattenInOrder(p.right()));
			
			return value;
		}
	};
	
	public List<T> flattenInOrder(Tree<T> tree) {
		List<T> value = new ArrayList<T>();
		if(tree==null)
			throw new IllegalArgumentException();
		
		if (tree.get().isLeft()) {
			T left = tree.get().ifLeft(funcLeft);
			if (left instanceof Tree.Leaf || left instanceof Tree.Node) {
				value.addAll(new MyFlattenTree<T>().flattenInOrder((Tree)left));
				
			} else {
//				System.out.println("left: "+left);
				value.add(left);
			}
			
		} else {
			value.addAll(tree.get().ifRight(funcRigth));
		}
		
		return value;
	}
}
