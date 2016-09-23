import java.util.*;

public class lab3 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.print("Enter your sentence: ");
		String sentence = in.nextLine();
		String binaryString = "";

		for (int i = 0; i < sentence.length(); i++)
		{
			int decimalValue = (int) sentence.charAt(i); 								// convert to decimal
			String binaryValue = Integer.toBinaryString(decimalValue); 				// convert to binary

			for (int j = 7; j > binaryValue.length(); j--)
			{
				binaryString += "0";
			}
			binaryString += binaryValue + " ";
		}
		System.out.println(binaryString);

		int[] freqArray = new int[256]; // an array to store all the frequencies

		for (int i = 0; i < sentence.length(); i++) { // go through the sentence
			freqArray[(int) sentence.charAt(i)]++; // increment the appropriate frequencies


		}

		PriorityQueue<Tree> PQ = new PriorityQueue<Tree>(); // make a priority queue to hold the forest of trees


		for (int i = 0; i < freqArray.length; i++)
		{
			if (freqArray[i] > 0)
			{

				System.out.println("'" + (char) i + "' appeared " + freqArray[i] + " times");
				Tree myTree = new Tree();

				myTree.frequency = freqArray[i];
				myTree.root = new Node();
				myTree.root.letter = (char) i;
				PQ.add(myTree);

			}
		}

		while (PQ.size() > 1)
		{
			Tree TreeA = PQ.poll();
			Tree TreeB = PQ.poll();
			Tree FinalTree = new Tree();

			FinalTree.frequency = TreeA.frequency + TreeB.frequency;
			FinalTree.root = new Node();

			FinalTree.root.leftChild = TreeA.root;
			FinalTree.root.rightChild = TreeB.root;

			PQ.add(FinalTree);
		}

		Tree huffmanTree = PQ.poll();

		int totalLength = 0; // Length of the new compressed sentence.
		String huffedCode;
		String newCode = "";
		int bitCount = 0;

		for (int i = 0; i < sentence.length(); i++)
		{
			huffedCode = huffmanTree.getCode(sentence.charAt(i));
			bitCount += huffedCode.length();

			if (newCode.length() != 0) // if the next bit of output is not equal to zero add a space
			{

				newCode += " ";
				bitCount++;
			}

			newCode += huffedCode;

			totalLength += huffedCode.length();
		}

		System.out.println("\n" + newCode);

		System.out.println("\n Compressed size is " + totalLength + "/" + sentence.length() * 7 + " bits = " + (double) ((totalLength * 100.0) / (sentence.length() * 7.0)) + "% \n");

	}

	static class Node
	{

		public char letter;

		public Node leftChild;
		public Node rightChild;

	}

	static class Tree implements Comparable<Tree>
	{
		public Node root;
		public int frequency = 0;

		public Tree() // constructor
		{
			root = null;
		}

		public int compareTo(Tree object)
		{ //
			if (frequency - object.frequency > 0)
				{ // compare the cumulative
													// frequencies of the tree
				return 1;
			} else if (frequency - object.frequency < 0)
				{
				return -1; // return 1 or -1 depending on whether these
							// frequencies are bigger or smaller
			} else {
				return 0; // return 0 if they're the same
			}
		}

		String path = "error";

		public String getCode(char letter) { // we want the code for this letter
			inOrder(root, letter, "");

			return path; // return the path

		}

		private void inOrder(Node localRoot, char letter, String path)
		{

			if (localRoot != null) // If root is null we've gone off the edge of the tree - TRY AGAIN
			{
				if (localRoot.letter == letter)
				{
					this.path = path;
				}

				else
				{
					inOrder(localRoot.leftChild, letter, path + "0");

					inOrder(localRoot.rightChild, letter, path + "1");
				}

			}
		}
	}
}