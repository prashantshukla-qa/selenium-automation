package com.qait.automation.coach.behavedemo.stepdefs;


import com.qait.automation.TestSessionInitiator;

public class BaseStepTest {
	public static TestSessionInitiator test;
	
    public static void baseStepTest() {
        test = new TestSessionInitiator();
        }
}
