/*Assignment 5
Krista Delap
February 28, 2018
Pac-Man Style Game*/

import java.util.Scanner;
import java.util.Random;

public class Project1_KDelap 
{
	//initialize grid
	public static void initGrid(char [][] grid, int x, int y)
	{	
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
			
				if(i == 0 && j == 0)
				{
					grid[i][j] = '>';
				}
				else
				{
					grid[i][j] = '.';
				}
			}
		}
		
		//setting up cookies		
		//determine how many cookies
		double cookies = (x*y)*.08;
		//set up for random generator
		Random rand = new Random();
		int rand1;
		int rand2;
		
		for(int k = 0; k < cookies; k++)
		{
			//get too randoms within grid bounds
			rand1 = rand.nextInt(x-1) + 1;
			rand2 = rand.nextInt(y-1) + 1;
			
			grid[rand1][rand2] = 'O';
		}
	}
	
	//print grid
	public static void printGrid(char [][] grid, int x, int y)
	{
		System.out.println();
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				System.out.print(grid[i][j]);
				if(j == y - 1)
				{
					System.out.println();
				}
			}
		}
		
		System.out.println();
	}
	
	//print menu
	public static void printMenu()
	{
		System.out.println("Commands");
		System.out.println("1. Menu");
		System.out.println("2. Turn Counterclockwise");
		System.out.println("3. Turn Clockwise");
		System.out.println("4. Move");
		System.out.println("5. Exit");
		System.out.println();
	}
	
	//change symbol based on direction
	public static void changeDir(char[][] grid, int posX, int posY, int dir)
	{
		char pac = '>';
		
		switch(dir)
		{
			case 1:
				pac = '>';
				break;
			case 2:
				pac = 'v';
				break;
			case 3:
				pac = '<';
				break;
			case 4:
				pac = '^';
				break;
		}
		
		grid[posX][posY] = pac;
	}
	
	public static void main(String args[])
	{
		//for getting use input throughout
		Scanner input = new Scanner(System.in);
		
		//get size of grid
		System.out.println("\n");
		System.out.println("Please enter the number of rows: ");
		int x = input.nextInt();
		System.out.println("Please enter the number of columns: ");
		int y = input.nextInt();
		
		//initialize grid
		char grid[][] = new char[x][y];
		initGrid(grid, x, y);
		
		//for tracking exit
		boolean cont = true;
		
		//for tracking Pac-Man direction & position
		int posX = 0;
		int posY = 0;
		int dir = 1;
		
		//tracking scores
		int cookieEaten = 0;
		int moves = 0;
		
		//game loop
		while(cont == true)
		{
			//print grid + Menu
			printGrid(grid, x, y);
			printMenu();
			
			//request next input
			System.out.println("Please enter the number of next move: ");
			int choice = input.nextInt();
			
			//exit if 5
			if(choice == 5)
			{
				cont = false;
			}
			
			//print instructions
			else if(choice == 1)
			{
				System.out.println("Use 2 or 3  to change the direction of Pac-Man.");
				System.out.println("Use 4  to move in the direction Pac-Man is facing.");
			}
			
			//change direction
			else if(choice == 3)
			{
				dir++;
				//reset if max
				if(dir == 5)
				{
					dir = 1;
				}

				changeDir(grid, posX, posY, dir);
				moves++;
			}
			else if(choice == 2)
			{
				dir--;
				//reset if min
				if(dir == 0)
				{
					dir = 4;
				}
				changeDir(grid, posX, posY, dir);
				moves++;
			}
			//move
			else if(choice == 4)
			{
				//clear current space
				grid[posX][posY] = ' ';
				
				switch(dir)
				{
				case 1:
					posY--;
					if(posY < 0)
					{
						posY = y-1;
					}
					break;
				case 2:
					posX--;
					if(posX < 0)
					{
						posX = x-1;
					}
					break;
				case 3:
					posY++;
					if(posY >= y)
					{
						posY = 0;
					}
					break;
				case 4:
					posX++;
					if(posX >= x)
					{
						posX = 0;
					}
					break;
				}
				
				//determine if cookie found
				if(grid[posX][posY] == 'O')
				{
					System.out.println("Cookie Eaten!");
					cookieEaten++;
				}
				
				//move Pac-Man
				changeDir(grid, posX, posY, dir);
				moves++;
			}
			//non valid input
			else
			{
				System.out.println("Please enter a valid choice 1-5");
			}
		}
		
		//print final scores
		System.out.println("Total Moves: " + moves);
		System.out.println("Total Cookies: " + cookieEaten);
		System.out.println("Total Average Moves per Cookie: " + moves/cookieEaten);
		
	}

}
