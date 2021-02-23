package com.mindtree.mathoperations.client;

import java.util.Scanner;

import com.mindtree.mathoperations.service.Service;
import com.mindtree.mathoperations.service.ServiceImpl;

public class MathApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Service ser = new ServiceImpl();
		int a, b, choice;
		do {
			System.out.println("1. Addition");
			System.out.println("2. Subtraction");
			System.out.println("3. Exit");
			System.out.println("Enter choice");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter 2 numbers");
				a = sc.nextInt();
				b = sc.nextInt();
				System.out.println("addition " + ser.addition(a, b));
				break;

			case 2:
				System.out.println("Enter 2 numbers");
				a = sc.nextInt();
				b = sc.nextInt();
				System.out.println("subtraction " + ser.subtraction(a, b));
				break;
			case 3:
				System.out.println("Thank you");
				break;

			default:
				System.out.println("Invalid");

			}

		} while (choice != 3);
		sc.close();
	}

}
