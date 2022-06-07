import base.Compressor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class LZ077EncoderDecoder 
{



	public static void main(String args[]) throws FileNotFoundException, IOException, InterruptedException
	{



		String line = "";
		String mishpat = "";
		
		System.out.println("Imports text from the file to compress ....\r\n"
				+ "");
		Thread.sleep(1000);
		
		FileReader reader = new FileReader("Romeo and Juliet  Entire Play.txt");
		BufferedReader br = new BufferedReader (reader);
		FileWriter writer = new FileWriter("compression.txt");
		BufferedWriter sr = new BufferedWriter (writer);
		FileReader reader1 = new FileReader("compression.txt");
		BufferedReader br1 = new BufferedReader (reader1);
		FileWriter writer1 = new FileWriter("reconstruction.txt");
		BufferedWriter sr1 = new BufferedWriter (writer1);
		
		while (line!=null) {
			line = br.readLine();
			if (line !=null)
				mishpat += line;
		}

		String s = "0";
		String t = "0";

		String input_names[] = new String [3];
		String output_names[] = new String [2];

//        System.out.println("The text is: " + mishpat);

		input_names[0] = mishpat;
		input_names[1] = s;
		input_names[2] = t;

		String [] input2 = Compress(input_names,output_names);


		sr.write(output_names[0]);
		sr.close();
		System.out.println("Text after compression: " + input2[0]);
		System.out.println("Saves the text after compression to new file called compression ");
		System.out.println("Number of compression threes created: " + input2[1] + "\n");

		
		System.out.println("Recovering the text from the compressed file ...");
		System.out.println("Saves to a new file called reconstruction\n");
		Decompress(input2);
 
		Thread.sleep(1000);
		System.out.println("The program is over");


	}




	public LZ077EncoderDecoder()
	{
		// TODO Auto-generated constructor stub
	}

	public static String[] Compress(String[] input_names, String[] output_names) throws IOException
	{

		
		String input = input_names[0] + '$';
		int triple = 0;
//		System.out.println(input);

		String newWord= null , checkWord = null,  checkWord2 = null; 
		int lastChar = 0, z ,s = 0,t = 0;
		char r = 0;
		int l=0;
		int count;
		String input2 = "";


		lastChar = 1;
		for (int i = 0; i < input.length(); i++) {
			count = 0 ;
			newWord = input.substring(0,i);
//			System.out.println(newWord);

			checkWord = input.substring(i,lastChar);
			l=0;  

			z = newWord.lastIndexOf(checkWord);

		

			if (lastChar >= input.length()) 	{

				i = input.length()  - 1;

			}
			if(z == -1 &&  input.charAt(lastChar + 1) !=  '$') {
				s = 0;
				t = 0;
				r = input.charAt(lastChar - 1);
				lastChar++;

			}


			if (z != -1 ) {
				l = z;
				

				checkWord2 = checkWord;

				while(l!=-1) {
                
					count++; 
					checkWord2 = checkWord2 + input.charAt(lastChar);
					
					l = newWord.lastIndexOf(checkWord2);
					
					lastChar++;
					if (l!=-1) {
						z=l;
					}
				}


				s = z + 1 ;
				t = count;
				r = input.charAt(lastChar - 1);  
				lastChar++;
				i = i + count;
			}



			triple++;
			input2 +="(" + s;

			input2 +="," + t + ",";

			input2 +=r + ")";





		}
		String triples2 = Integer.toString(triple);


		output_names[0] = input2;
		output_names[1] = triples2;

		return output_names;
	}

	public static void Decompress(String[] output_names) throws IOException
	{
		String input2 = output_names[0];
		FileWriter writer1 = new FileWriter("reconstruction.txt");
		BufferedWriter sr1 = new BufferedWriter (writer1);
		
		int tripler = Integer.parseInt(output_names[1]);
		int end2 = Integer.parseInt(output_names[1]);
		int count = 0;

		String newWord2 = "";
		boolean end = true;
		int i = 0, s1= 0 ,t1 = 0;
		char ot = 'a';


		int j = 1;
		char check = input2.charAt(j);

		while (end) {
			s1=0;
			t1=0;
			check = input2.charAt(j);

			while (check!='(' && check != ',' && check!=')') {        
				s1 = s1 +  (input2.charAt(j) - 48) ;   
				j++;
				check = input2.charAt(j);
				if (check !=',' && check != ')' && check!='(') {                 
					s1 *= 10;
				}
			}

			
			j++;
			check = input2.charAt(j);
			while (check!='(' && check != ',' && check!=')') {
				t1 += input2.charAt(j) - 48 ;      
				
				j++;
				check = input2.charAt(j);
				if (check !=',' && check != ')' && check!='(') {
					t1 *= 10;
				}
			}
			
			j++;
			check=input2.charAt(j);
			ot = input2.charAt(j);
//			System.out.println(s1 + " " + t1 + " " + ot );
			j=j+3;
			

			if ( s1 == 0) {
				newWord2 += ot;
//				System.out.println(newWord2);
			}

			else {
				for (int k = 0; k < t1; k++) {
					newWord2+= newWord2.charAt(s1+k-1);
//					System.out.println(newWord2);
				}
				newWord2 += ot;
			}


			count++;
			if (count == tripler ) {
//				System.out.println(newWord2);
				sr1.write(newWord2);
				sr1.close();
				end = false;

			} 

		}
	}




	public byte[] CompressWithArray(String[] input_names, String[] output_names)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] DecompressWithArray(String[] input_names, String[] output_names)
	{
		// TODO Auto-generated method stub
		return null;
	}



}