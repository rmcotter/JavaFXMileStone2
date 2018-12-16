import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to many different
 * indexes of a large data set. BPTree objects are created for each type of
 * index needed by the program. BPTrees provide an efficient range search as
 * compared to other types of data structures due to the ability to perform
 * log_m N lookups and linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu) & Rick Cotter
 *
 * @param <K>
 *            key - expect a string that is the type of id for each item
 * @param <V>
 *            value - expect a user-defined type that stores all data for a food
 *            item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

	// Root of the tree
	private Node root;

	// Branching factor is the number of children nodes
	// for internal nodes of the tree
	private final int branchingFactor;

	/**
	 * Public constructor
	 * 
	 * @param branchingFactor
	 */
	public BPTree(int branchingFactor) {
		if (branchingFactor <= 2) {
			throw new IllegalArgumentException("Illegal branching factor: " + branchingFactor);
		}
		this.branchingFactor = branchingFactor;
		root = new LeafNode();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void insert(K key, V value) {
		root.insert(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
	 */
	@Override
	public List<V> rangeSearch(K key, String comparator) {
		if (!comparator.contentEquals(">=") && !comparator.contentEquals("==") && !comparator.contentEquals("<="))
			return new ArrayList<V>();

		List<V> returnLIst = root.rangeSearch(key, comparator);

		return returnLIst;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		Queue<List<Node>> queue = new LinkedList<List<Node>>();
		queue.add(Arrays.asList(root));
		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
			while (!queue.isEmpty()) {
				List<Node> nodes = queue.remove();
				sb.append('{');
				Iterator<Node> it = nodes.iterator();
				while (it.hasNext()) {
					Node node = it.next();
					sb.append(node.toString());
					if (it.hasNext())
						sb.append(", ");
					if (node instanceof BPTree.InternalNode)
						nextQueue.add(((InternalNode) node).children);
				}
				sb.append('}');
				if (!queue.isEmpty())
					sb.append(", ");
				else {
					sb.append('\n');
				}
			}
			queue = nextQueue;
		}
		return sb.toString();
	}

	/**
	 * This abstract class represents any type of node in the tree This class is a
	 * super class of the LeafNode and InternalNode types.
	 * 
	 * @author sapan
	 */
	private abstract class Node {

		// List of keys
		List<K> keys;

		/**
		 * Package constructor
		 */
		Node() {

			keys = new ArrayList<K>();
		}

		/**
		 * Inserts key and value in the appropriate leaf node and balances the tree if
		 * required by splitting
		 * 
		 * @param key
		 * @param value
		 */
		abstract void insert(K key, V value);

		/**
		 * Gets the first leaf key of the tree
		 * 
		 * @return key
		 */
		abstract K getFirstLeafKey();

		/**
		 * Gets the new sibling created after splitting the node
		 * 
		 * @return Node
		 */
		abstract Node split();

		/*
		 * (non-Javadoc)
		 * 
		 * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
		 */
		abstract List<V> rangeSearch(K key, String comparator);

		/**
		 * 
		 * @return boolean
		 */
		abstract boolean isOverflow();

		public String toString() {
			return keys.toString();

		}

		/*
		 * This is used for preventing out of bounds exceptions. The indexlocation is
		 * the index of where the value should exist within a collection
		 * 
		 * @author Rick Cotter
		 */
		protected int adjustIndexLocation(int indexLocation) {
			if (indexLocation >= 0)
				indexLocation++;
			else
				indexLocation = -indexLocation - 1;
			return indexLocation;
		}

		/*
		 * The root action we take when we hit an overflow. Will create a sibling node
		 * and update the root with the new node
		 * 
		 * @author Rick Cotter
		 */
		private void OverFlowActionRoot() {
			Node sibling = split();
			InternalNode newRoot = new InternalNode();
			updateParent(sibling, newRoot);
			root = newRoot;

		}

		/*
		 * The main overflowAction tag. This will call root overflow if we have an
		 * overflow on the root, otherwise it will hand it off to internal or leaf
		 * processes and update the parent.
		 * 
		 * @author Rick Cotter
		 */
		protected void OverFlowAction() {
			if (root == this) {
				OverFlowActionRoot();
				return;
			}
			InternalNode parent = findParentNode();
			Node sibling = split();
			
			updateParent(sibling, parent);

		}

		/*
		 * This is used to find the parent of a node.
		 * 
		 * @author Rick Cotter
		 */
		@SuppressWarnings("unchecked")
		private InternalNode findParentNode() {
			InternalNode tempnode = null;

			tempnode = (BPTree<K, V>.InternalNode) root;
			if (tempnode.children.contains(this))
				return tempnode;
			else {
				return findParentRecursive(tempnode);

			}
		}

		/*
		 * An internal helper method to find the parent node.
		 * 
		 * @author Rick Cotter
		 */
		@SuppressWarnings("unchecked")
		private InternalNode findParentRecursive(InternalNode intNode) {
			int i = 0;
			
			
			if (intNode.children.contains(this)){
				return intNode;
			}
			
			K thiskey = this.keys.get(0);
		
			for (K internalKey : intNode.keys) {
				
				if (thiskey.compareTo(internalKey)<0)
					return findParentRecursive((BPTree<K, V>.InternalNode) intNode.children.get(i));
				i++;
			}
			
			try{
			return findParentRecursive((BPTree<K, V>.InternalNode) intNode.children.get(i));
			}
			catch(Exception e){
				return intNode;
			}
		}

		/*
		 * A helper method to add information to the parent node.
		 * 
		 * @author Rick Cotter
		 */
		private void updateParent(Node sibling, InternalNode parent) {
			parent.keys.add(sibling.getFirstLeafKey());
			Collections.sort(parent.keys);
			if (!parent.children.contains(this))parent.children.add(this);
			List<Node> tempChildren = new ArrayList<Node>();
			
			 tempChildren =parent.children;
			List<Node> tempChildren2 = new ArrayList<Node>();
			
			
			for (Node tempNode : tempChildren)
			{
				if(tempNode.keys.get(0).compareTo(sibling.keys.get(0))<=0
						|| tempChildren2.contains(sibling)){
					tempChildren2.add(tempNode);
					
				}
				else{
					tempChildren2.add(sibling);
					tempChildren2.add(tempNode);
				}
				
			}
			if(!tempChildren2.contains(sibling))tempChildren2.add(sibling);
			
			
			
			parent.children = tempChildren2;
		
		}

	} // End of abstract class Node

	/**
	 * This class represents an internal node of the tree. This class is a concrete
	 * sub class of the abstract Node class and provides implementation of the
	 * operations required for internal (non-leaf) nodes.
	 * 
	 * @author sapan & Rick Cotter
	 */
	private class InternalNode extends Node {

		// List of children nodes
		List<Node> children;

		/**
		 * Package constructor
		 */
		InternalNode() {
			super();
			children = new ArrayList<Node>();

		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 */
		K getFirstLeafKey() {

			return children.get(0).getFirstLeafKey();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#isOverflow()
		 */
		boolean isOverflow() {

			return children.size() > branchingFactor;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
		 */
		void insert(K key, V value) {
			int indexLocation = Collections.binarySearch(keys, key);

			if (isValueInChildren(value, indexLocation)) {
				// update the value if we find it
				children.get(indexLocation).insert(key, value);

			} else { // otherwise put it where it should be
				indexLocation = adjustIndexLocation(indexLocation);
				children.get(indexLocation).insert(key, value);

			}
			if (isOverflow()) {
				OverFlowInternalAction();
			}

		}

		/*
		 * When overflowing in internal node we have to be careful since it won't
		 * contain the key that gets propogated
		 * 
		 * @author Rick Cotter
		 */
		private void OverFlowInternalAction() {
			if (this == root) {// special split
				threeWaySplit();

				return;

			}
			super.OverFlowAction();

		}

		/*
		 * 
		 * @author Rick Cotter
		 */
		private void threeWaySplit() {
			InternalNode oldRoot = new InternalNode();
					oldRoot =this;
			InternalNode lS = new InternalNode();
			InternalNode rS = new InternalNode();
			int numKeys = keys.size();
			int middleKeyIndex = (numKeys / 2) + 1;
			int numChild = children.size();
			int numChildIndex = (numChild / 2);

			lS.keys.addAll(oldRoot.keys.subList(0, middleKeyIndex - 1));
			rS.keys.addAll(oldRoot.keys.subList(middleKeyIndex, numKeys));

			lS.children.addAll(oldRoot.children.subList(0, numChildIndex));
			rS.children.addAll(oldRoot.children.subList(numChildIndex, numChild));

			K tempKey = oldRoot.keys.get(middleKeyIndex-1);
			keys.removeAll(oldRoot.keys.subList(0, numKeys));
			//keys.removeAll(oldRoot.keys.subList(0, middleKeyIndex - 1));

			this.keys.add(tempKey);
			children = new ArrayList<Node>();
			this.children.add(lS);
			this.children.add(rS);
			root = this;
		}

		/*
		 * 
		 * @author Rick Cotter
		 */
		private boolean isValueInChildren(V value, int indexLocation) {
			return indexLocation >= 0 && children.get(indexLocation).equals(value);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#split()
		 */
		Node split() {
			InternalNode sibling = new InternalNode();
			int numKeys = keys.size();
			int middleKeyIndex = (numKeys / 2) + 1;

			sibling.keys.addAll(keys.subList(middleKeyIndex, numKeys));
			for(int i=middleKeyIndex-1; i<numKeys; i++)
			{
				keys.remove(middleKeyIndex-1);
			}
			
			//keys.removeAll(keys.subList(middleKeyIndex - 1, numKeys));

			sibling.children.addAll(children.subList(middleKeyIndex, numKeys + 1));
			children.removeAll(children.subList(middleKeyIndex, numKeys + 1));

			return sibling;

		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
		 */
		List<V> rangeSearch(K key, String comparator) {
			int i = 0;

			for (K internalKey : keys) {
				if (key.compareTo(internalKey) <= 0)
					return children.get(0).rangeSearch(key, comparator);
				i++;
			}

			return children.get(i).rangeSearch(key, comparator);
		}

	} // End of class InternalNode

	/**
	 * This class represents a leaf node of the tree. This class is a concrete sub
	 * class of the abstract Node class and provides implementation of the
	 * operations that required for leaf nodes.
	 * 
	 * @author sapan
	 */
	private class LeafNode extends Node {

		// List of values
		List<V> values;

		// Reference to the next leaf node
		LeafNode next;

		/**
		 * Package constructor
		 */
		LeafNode() {
			super();

			values = new ArrayList<V>();
			next = null;

		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 */
		K getFirstLeafKey() {

			return keys.get(0);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#isOverflow()
		 */
		boolean isOverflow() {
			return values.size() > branchingFactor - 1;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#insert(Comparable, Object)
		 */
		void insert(K key, V value) {

			int indexLocation = Collections.binarySearch(keys, key);

			if (isValueInCollection(value, indexLocation)) {
				// update the value if we find it
				values.set(indexLocation, value);
			} else { // otherwise put it where it should be
				indexLocation = adjustIndexLocation(indexLocation);
				addKeyValuePair(key, value, indexLocation);
			}
			if (isOverflow()) {
				OverFlowAction();
			}
		}

		/*
		 * 
		 * @author Rick Cotter
		 */
		private void addKeyValuePair(K key, V value, int indexLocation) {
			keys.add(indexLocation, key);
			values.add(indexLocation, value);
		}

		/*
		 * 
		 * @author Rick Cotter
		 */
		private boolean isValueInCollection(V value, int indexLocation) {
			return indexLocation >= 0 && values.get(indexLocation).equals(value);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#split()
		 */
		Node split() {
			LeafNode sibling = new LeafNode();
			int numKeys = keys.size();
			int middleKeyIndex = (numKeys / 2);
			for (int i = middleKeyIndex; i < numKeys; i++) {
				// push values over to next node
				sibling.keys.add(this.keys.get(i));
				sibling.values.add(this.values.get(i));

			}

			// clean up the keys and values from this node
			for (int i = middleKeyIndex; i < numKeys; i++) {
				keys.remove(middleKeyIndex);
				values.remove(middleKeyIndex);

			}

			// update next pointer
			sibling.next = next;
			next = sibling;
			return sibling;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#rangeSearch(Comparable, String)
		 */
		List<V> rangeSearch(K key, String comparator) {
			List<V> returnLIst = new ArrayList<V>();
			List<K> currentKeys;
			List<V> currentValues;
			LeafNode node = this;
			int indexLocation;

			while (node != null) {
				currentKeys = node.keys;
				currentValues = node.values;
				switch (comparator.toString()) {
				case ">=":
					indexLocation = Collections.binarySearch(currentKeys, key);
					indexLocation = areKeysBigger(key, node, indexLocation);
					if (indexLocation >= 0) {
						tryToAddKeyToReturnListGT(returnLIst, currentValues, indexLocation);

					}

					break;
				case "==":
					indexLocation = Collections.binarySearch(currentKeys, key);
					if (indexLocation >= 0) {
						Iterator<K> keyIteration = currentKeys.listIterator(indexLocation);
						Iterator<V> valueIteration = currentValues.listIterator(indexLocation);
						while (keyIteration.hasNext()) {

							tryToAddNextKeyToReturnListEQ(key, returnLIst, keyIteration, valueIteration);
						}
					}
					break;
				case "<=":
					indexLocation = Collections.binarySearch(keys, key);
					if (indexLocation < 0 && node.getFirstLeafKey().compareTo(key) <= 0)
						indexLocation = 0;
					if (indexLocation >= 0) {
						ListIterator<K> keyIteration = currentKeys.listIterator(indexLocation);
						ListIterator<V> valueIteration = currentValues.listIterator(indexLocation);
						// increment up the list one to include since it is <=
						moveIteratorsBackOne(keyIteration, valueIteration);

						while (keyIteration.hasNext()) {
							tryToAddNextKeyToReturnListLT(key, returnLIst, keyIteration, valueIteration);
						}

					}
					break;

				}
				node = node.next;
			}
			return returnLIst;
		}

		/**
		 * Used when finding keys bigger than the key passed in. Adjusts indexLocation
		 * so that we keep searching if key is not in this collection
		 * 
		 * @author Rick Cotter
		 */
		private int areKeysBigger(K key, LeafNode node, int indexLocation) {
			if (indexLocation < 0 && node.getFirstLeafKey().compareTo(key) >= 0)
				indexLocation = 0;
			return indexLocation;
		}

		/**
		 * keeps adding the remaining keys to the return list
		 * 
		 * @author Rick Cotter
		 */
		private void tryToAddKeyToReturnListGT(List<V> returnLIst, List<V> currentValues, int indexLocation) {
			ListIterator<V> valueIteration = currentValues.listIterator(indexLocation);

			while (valueIteration.hasNext()) {
				V insertVal = valueIteration.next();
				returnLIst.add(insertVal);
			}
		}

		/**
		 * Evaluates if the key == currentKey before adding to returnList
		 * 
		 * @author Rick Cotter
		 */
		private void tryToAddNextKeyToReturnListEQ(K key, List<V> returnLIst, Iterator<K> keyIteration,
				Iterator<V> valueIteration) {
			K currentKey = keyIteration.next();
			V currentVal = valueIteration.next();
			if (key.equals(currentKey)) {
				returnLIst.add(currentVal);
			}
		}

		/**
		 * Evaluates if the key is <= currentKey before adding to returnList
		 * 
		 * @author Rick Cotter
		 */
		private void tryToAddNextKeyToReturnListLT(K key, List<V> returnLIst, ListIterator<K> keyIteration,
				ListIterator<V> valueIteration) {
			K currentKey = keyIteration.next();
			V currentVal = valueIteration.next();
			if (key.equals(currentKey) || currentKey.compareTo(key) < 0) {
				returnLIst.add(currentVal);
			}
		}

		/**
		 * Used to adjust the iterators in order to get them to point to the correct
		 * index
		 * 
		 * @author Rick Cotter
		 */
		private void moveIteratorsBackOne(ListIterator<K> keyIteration, ListIterator<V> valueIteration) {
			if (keyIteration.hasPrevious()) {
				keyIteration.previous();
				valueIteration.previous();
			}
		}

	} // End of class LeafNode

	/**
	 * Contains a basic test scenario for a BPTree instance. It shows a simple
	 * example of the use of this class and its related types.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create empty BPTree with branching factor of 3
		BPTree<Double, Double> bpTree = new BPTree<>(3);

		// create a pseudo random number generator
		Random rnd1 = new Random();

		// some value to add to the BPTree
		Double[] dd = { 0.0d, 0.5d, 0.2d, 0.8d, 0.9d, 0.4d, 0.7d, 0.4d, 0.4d };

		// build an ArrayList of those value and add to BPTree also
		// allows for comparing the contents of the ArrayList
		// against the contents and functionality of the BPTree
		// does not ensure BPTree is implemented correctly
		// just that it functions as a data structure with
		// insert, rangeSearch, and toString() working.
		List<Double> list = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			Double j = dd[i];
			list.add(j);
			bpTree.insert(j, j);
			System.out.println("\n\nTree structure:\n" + bpTree.toString());
		}
		List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
		System.out.println("Filtered values: " + filteredValues.toString());
	}

} // End of class BPTree