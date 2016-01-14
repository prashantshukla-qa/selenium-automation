/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.coach.behavedemo.storyrunners;

import java.net.MalformedURLException;
import static com.qait.automation.utils.ConfigPropertyReader.checkIfValueIsNull;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.coach.behavedemo.getstory.Constants;
import com.qait.automation.coach.behavedemo.getstory.JiraSprintStoryFinder;
import com.qait.automation.coach.behavedemo.getstory.JiraStoryDownloader;
import com.qait.automation.coach.behavedemo.getstory.JiraStoryID;
import com.qait.automation.coach.behavedemo.stepdefs.AccountPage_Stepdef;
import com.qait.automation.coach.behavedemo.stepdefs.CountryHomePage_Stepdef;
import com.qait.automation.coach.behavedemo.stepdefs.LoginSteps;
import com.qait.automation.utils.FileHandler;
import com.qait.demo.tests.Account_Creation_Test;

/**
 *
 * @author prashantshukla
 */
public class StoryTest extends JUnitStories {

	TestSessionInitiator test;

	private final CrossReference xref = new CrossReference();

	public StoryTest() {
		FileHandler.cleanStoryLocation();
		System.out.println("=======================================");
		System.out.println("Fetching Feature files for JIRA stories");
		System.out.println("=======================================");
		try {
			for (String eachStory : getListOfStories()) {
				if (eachStory != null) {
					new JiraStoryDownloader(eachStory).storeJiraStoryLocally();

				} else {

					JiraSprintStoryFinder sprintStories = new JiraSprintStoryFinder(System.getProperty("rapidView"));
					System.out.println("Sprint Stories:" + sprintStories);

					sprintStories.getJiraSprintStories().stream().map((sprintStory) -> {return sprintStory;}).map((sprintStory) -> new JiraStoryDownloader(sprintStory)).forEach((jirastory) -> {
						jirastory.storeJiraStoryLocally();
					});
				}
			}

			configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(true)
					.doIgnoreFailureInView(true).useThreads(1).useStoryTimeoutInSecs(10000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<String> getListOfStories() {
		ArrayList<String> allJIRAStories = new ArrayList<String>();

		String storyIdList = System.getProperty("storyId");
		String taskIdList = System.getProperty("taskId");

		if (!checkIfValueIsNull(storyIdList) && checkIfValueIsNull(System.getProperty("component"))) {
			for (String eachId : storyIdList.split(",")) {
				allJIRAStories.add(Constants.JIRA_PROJECT_ID + "-" + eachId);
			}
		} else if (!checkIfValueIsNull(taskIdList)) {
			allJIRAStories = JiraStoryID.getAllStoryIdsForTask();
		} else {
			allJIRAStories = JiraStoryID.getAllStoryIds();
		}
		return allJIRAStories;
	}

	@Override
	public Configuration configuration() {

		Class<? extends Embeddable> embeddableClass = this.getClass();
		Properties viewResources = new Properties();
		viewResources.put("decorateNonHtml", "true");
		ParameterConverters parameterConverters = new ParameterConverters();
		ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(),
				new LoadFromClasspath(embeddableClass), parameterConverters);
		// add custom converters
		parameterConverters.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
				new ParameterConverters.ExamplesTableConverter(examplesTableFactory));

		URL storyURL = null;
		try {
			storyURL = new URL("file://" + System.getProperty("user.dir") + "/" + Constants.STORY_LOC);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return new MostUsefulConfiguration()
				.useStoryControls(new StoryControls().doDryRun(false).doSkipScenariosAfterFailure(false))
				.useStoryLoader(
						new LoadFromRelativeFile(storyURL))
				.useStoryParser(
						new RegexStoryParser(
								examplesTableFactory))
				.useStoryPathResolver(new UnderscoredCamelCaseResolver())
				.useStoryReporterBuilder(new StoryReporterBuilder()
						.withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass)).withDefaultFormats()
						.withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
						.withViewResources(viewResources).withFormats(Format.ANSI_CONSOLE, Format.XML, Format.HTML)
						.withFailureTrace(true).withFailureTraceCompression(true).withCrossReference(xref))
				.useParameterConverters(parameterConverters).useStepMonitor(xref.getStepMonitor());
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), new LoginSteps(), new AccountPage_Stepdef(), new CountryHomePage_Stepdef());
	}

	@Override
	protected List<String> storyPaths() {
		URL codeLocation = CodeLocations.codeLocationFromPath(Constants.STORY_LOC);
		List<String> paths = null;
		if (checkIfValueIsNull(System.getProperty("storyId")) || System.getProperty("storyId").split(",").length > 0) {
			paths = new StoryFinder().findPaths(codeLocation, Arrays.asList("/*.story"), Arrays.asList(""));
		} else {
			paths = new StoryFinder().findPaths(codeLocation, System.getProperty("storyId") + ".story", "");
		}
		return paths;
	}
}
