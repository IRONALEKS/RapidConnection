package Utils;

public class IntegerStuff
{
	private static final int array[] = {10,100,1000,10000,100000,1000000,10000000,100000000,1000000000};
	
	private static final char ENCODE_64[] = {
			'0','1','2','3','4','5','6','7',
			'8','9','a','b','c','d','e','f',
			'g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v',
			'w','x','y','z','A','B','C','D',
			'E','F','G','H','I','J','K','L',
			'M','N','O','P','Q','R','S','T',
			'U','V','W','X','Y','Z','[',']'
	};
	
	private static final char ENCODE_100[] = {
			'0','1','2','3','4','5','6','7',
			'8','9','a','b','c','d','e','f',
			'g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v',
			'w','x','y','z','A','B','C','D',
			'E','F','G','H','I','J','K','L',
			'M','N','O','P','Q','R','S','T',
			'U','V','W','X','Y','Z','[',']',
			' ','!','"','#','$','%','&','\'',
			'(',')','*','+',',','-','.','/',
			':',';','<','=','>','?','@','\\',
			'^','_','`','{','}','~','\r','\n',
			'\0','\b','\t','\f'
	};
	
	private static final int DECODE_64[] = {
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			0,1,2,3,4,5,6,7,8,9,-1,
			-1,-1,-1,-1,-1,-1,36,37,
			38,39,40,41,42,43,44,45,
			46,47,48,49,50,51,52,53,
			54,55,56,57,58,59,60,61,
			62,-1,63,-1,-1,-1,10,11,
			12,13,14,15,16,17,18,19,
			20,21,22,23,24,25,26,27,
			28,29,30,31,32,33,34,35
	};
	
	private static final int DECODE_100[] = {
			96,-1,-1,-1,-1,-1,-1,-1,
			97,98,95,-1,99,94,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			64,65,66,67,68,69,70,71,
			72,73,74,75,76,77,78,79,
			0,1,2,3,4,5,6,7,8,9,80,
			81,82,83,84,85,86,36,37,
			38,39,40,41,42,43,44,45,
			46,47,48,49,50,51,52,53,
			54,55,56,57,58,59,60,61,
			62,87,63,88,89,90,10,11,
			12,13,14,15,16,17,18,19,
			20,21,22,23,24,25,26,27,
			28,29,30,31,32,33,34,35,
			91,-1,92,93
	};
	
	public static String encodeBaseX(int num,int base)
	{
		char result[] = new char[31];
    	
    	int size = 0;
    	
    	do
    	{
    		result[size] = ENCODE_100[num%base];
    		num/=base;
    		size+=1;
    	}while(num!=0);
    	
    	return new String(result, 0, size);
	}
	
	public static String encodeBase100(int num)
    {
    	char result[] = new char[5];
    	
    	int size = 0;
    	
    	do
    	{
    		result[size] = ENCODE_100[num%100];
    		num/=100;
    		size+=1;
    	}while(num!=0);
    	
    	return new String(result, 0, size);
    }
	
	public static String encodeBase64(int num)
    {
    	char result[] = new char[6];
    	
    	int size = 0;
    	
    	do
    	{
    		result[size] = ENCODE_64[num%64];
    		num/=64;
    		size+=1;
    	}while(num!=0);
    	
    	return new String(result, 0, size);
    }
	
	public static int decodeBaseX(String input,int base)
    {
    	int result = 0;

    	for(int a = input.length()-1;a>-1;a--)
    	{
    		char ch = input.charAt(a);
    		int value = DECODE_100[ch];
        	result*=base;
        	result+=value;
    	}
    	
    	return result;
    }
	
	public static int decodeBase100(String input)
    {
    	int result = 0;

    	for(int a = input.length()-1;a>-1;a--)
    	{
    		char ch = input.charAt(a);
    		int value = DECODE_100[ch];
        	result*=100;
        	result+=value;
    	}
    	
    	return result;
    }
	
	public static int decodeBase64(String input)
    {
    	int result = 0;

    	for(int a = input.length()-1;a>-1;a--)
    	{
    		char ch = input.charAt(a);
    		int value = DECODE_64[ch];
        	result*=64;
        	result+=value;
    	}
    	
    	return result;
    }
	
	public static int getLengthOfNumberWithString(int num)
	{
		String str = Integer.toString(num);
		return str.length();
	}
	
	public static int getLengthOfNumberWithDivide(int num)
	{
		int result = 0;
		
		do
		{
			num = num/10;
			result+=1;
		}while(num!=0);
		
		return result;
	}
	
	public static int getLengthOfNumberWithIf(int num)
	{
		if(num<10)
		{
			return 1;
		}
		else if(num<100)
		{
			return 2;
		}
		else if(num<1000)
		{
			return 3;
		}
		else if(num<10000)
		{
			return 4;
		}
		else if(num<100000)
		{
			return 5;
		}
		else if(num<1000000)
		{
			return 6;
		}
		else if(num<10000000)
		{
			return 7;
		}
		else if(num<100000000)
		{
			return 8;
		}
		else if(num<1000000000)
		{
			return 9;
		}
		else
		{
			return 10;
		}
	}
	
	public static int getLengthOfNumberWithArray(int num)
	{
		for(int a = 0;a<array.length;a++)
		{
			int value = array[a];
			if(num<value)
			{
				return a+1;
			}
		}
		
		return 10;
	}
}
