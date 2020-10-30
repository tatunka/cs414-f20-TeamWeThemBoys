# Scrum Outputs

### <u>9/29 Beginning Development</u>

We decided on using Spring and its suite that is designed to work in Eclipse. This will also make setup and use with Maven mush easier. Front-end devs using React can use IntelliJ for convenience to them. The service will query MariaDB and send info to front end. Sprints will last two weeks. 

### Planning
By task, notes on discussion and task outcomes are in the kanban boards. 

* As a new user I would like to register for the platform 

    * Anyone could register to this platform, for example by using an email, which would be unique for that user. To register, the person should provide a nickname (also unique, maybe public???) and a password 

    * Unique email 

    * Unique nickname 

    * Password 

* As a user, I would like to be able to log out of the platform 

* As a user, I would like to be able to log into the platform 

* As a user, I would like to be able to unregister from the platform 

    * I think a user would also want to be able to unregister 

* As a player, I would like to create a new match 

    * She could create a new match (so she can play it) 

    * Must be able to play multiple matches async 

* As a user, I would like to invite other players to a match 

     * Since she can't play by herself, she should be able to invite another user to join the match. Perhaps she could send more than one invitation, and then it would be something like "first come, first served", so the first user accepting the invitation will be the one joining the match??? Is that possible?? 

    * First come first serve on invites 

* As a player, I would like to be able to accept invitations to matches 

* As a player, I would like to be able to reject invitations to matches 

    * I guess a user also needs to be able to reject an invitation, so it would be nice if the user who sent it receives a notification. 

    * Send notification of rejection 

* As a player, I would like to be able to quit a match. 

    * It would be cool if a user could be part of multiple games at the same time, though maybe she would want to quit from any game at any time? 

    * Abandon match 

    * Add abandoned match to history or note match has been abandoned 

* As a user, I would like to be able to view my profile 

    * The platform also needs to record the history of matches played by a user. Info like players, start and end dates and times, and end results would be useful, you know, to know who won or lost or if there was a tie. I guess info about abandoned games should be also recorded. All this info would be part of the user profile, which can only be viewed by registered users. 

    * User must be registered. 

    * View Match history 

    * What else is in user profile? 

    * Can view others profiles? 

* As a player, I want to play Legan chess 

    * The gameplay, well—the xGame has some rules 2 that need to be followed during a match. Besides that, of course a game can't start until enough players have joined, and I'm guessing that after a match starts no other player should be able to join. Who starts the match? If I'm not wrong, that should be specified in the rules of the game. Otherwise, the user who created the match would be the one making the first move. Mmmm, the system should be able to determine whose turn is it... according to the rules, right? Meaning, a player can only make moves when it’s her turn... allowed moves, that is... the rules. 

* As a player, I want to exit a match to resume later 

    * What else? Oh right. The state of the matches should be saved in some way, so the user can play whenever she wants. My guess is that users won't be playing the whole time, so for example, a user would make a move whenever is her turn and log out, and after a while she would come back and check if the other player made a move and it’s her turn again. Asynchronous matches, I think that describes it. 

* As a player, I want to resume a previously started match 

* As a player, I want to be notified about the outcome of a match 

    * The system needs to know when a game is over and should let know the players who won or lost. All according to the rules.”  

  

#### 9/29 Scrum

Alex: Planning React layout  
Joe: Starting Spring skeleton  
Jesse: Starting React skeleton  
Lance: Researching hibernate, getting ready to integrate it  
Westin: Working with Dr. Moreno to set up database  

#### 10/6 Scrum

Alex: Swapping React skeleton to functional components  
Joe: Setting up Spring, will handle database transactions, Westin and Lance can hold off  
Jesse: Swapping React skeleton to functional components  
Lance, Westin: Holding off for Spring skeleton to work on services  


### <u>10-12-20 End of Sprint</u>

Spring framework is done, react login and design are currently going, legan engine 

###### Sprint Review 

* This sprint saw a lot of design and initial skeleton work done, but the kanban board did not represent this. More tasks should be made for each piece of work being done, and the board should be kept current.  

* Moving forward, more tasks that are specific to the work being done. Importantly, continue to carry over tasks from user stories without getting lost in development. Continue linking PRs to tasks in DevOps to maintain concurrency. 

* Moving forward, be more careful not to duplicate tasks. Consider existing tasks during discussions of making new ones.  

###### Sprint Retrospective 

* We discussed the front end design well. How it looks will reflect what we want, thanks to effectively communicating goals and ideas.  

* We should update teammates more on progress and where we are with each task 

* To improve here, we will be more consistent with sending daily updates (even if we don't have a significant update) 

#### 10/13 Scrum

Alex, Jesse: Nothing to update on  
Joe: Starting on match service, match creation  
Lance: Worked on front end login, registration. Moving on to message navigation bar  
Westin: Adapting chess engine to Legan style. Added Queen and Knight moves  

#### 10/15 Scrum

Alex: Working on match creation and selection  
Joe: Finished user search service, moving on to retrieving match invitation service  
Jesse: Finishing up Match UI  
Lance: Finished navigation bar, moving to login service  
Westin: Starting pawn promotion  

#### 10/16 Scrum

Alex: Nothing to Update  
Joe: Finished match invitaiton services, match acceptance. Starting dev/user guide  
Jesse: Fine tuning side bars and basic match view  
Lance: Working on login service, blocking on handling password with UserController  
Westin: Finishing pawn promotion, moving to king checking  

#### 10/20 Scrum

Joe & Westin: Nothing to update  
Alex: Finished match creation, moving to selection funcitonality  
Jesse: Working on Board functionality, more complicated than expected  
Lance: Finishing work on login/registration services  

#### 10/21 Scrum

Alex: Making chnages on PR, moving to next task (unspecified)  
Joe: done with tasks, providing help to rest of team  
Jesse: Mixed tasks for match UI and overall UI, finishing both  
Lance: No progress on task, added reviews to multiple current PRs  
Westin: King check moved to multiple tasks and put on back burner, moved on to match state retrieval  

#### 10/24 Scrum

Alex: Completing PR changes, working on sending HTTP front end requests  
Joe: Done with tasks, providing help with tasks  
Jesse: Finished match screen, starting on logout UI  
Lance: Finishing login, registration services  
Westin: Reviewing service code to refamiliarize and work on tasks  

### <u>10-26-20 End of Sprint</u>

Legan chess engine works with basic rules. The application now has UI for user registration, log in. Services now exist for player search, match creation, and match invitations. 

Services for registration, login, match state, and UI for match and match selection, logout are incomplete for this sprint.   

###### Sprint Review 

* We underestimated the complexity of the chess engine. This was resolved by splitting up the rest of the engine features into multiple tasks.  

* We also underestimated a handful of other tasks. This was resolved by adjusting time estimates, moving people to whatever tasks are open, and moving some tasks to the next sprint. This adjustment was expected after the first couple of sprints.  

* Pull requests taking too long to get reviewed by three people and then merged. This was resolved by requiring one fewer approval. 

###### Sprint Retrospective 

* What went well 

    * Good team interactions & assistance when people ran into issues 

    * Good & consistent communication 

    * Good communication between people working on different parts of the application 

    * Team members are following the scrum expectations well (join the live scrums when you can, written update when you cannot)

    * Got a majority of the baseline functionality done 

* What could be improved 

    * Pull requests took to long to get reveiwed merged

* What we will commit to improve in the next sprint 

    * Shift expectation to look at pull requests within 2 days, in addition to requiring only 2 reviews 
