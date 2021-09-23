# BowlingScoreApp
Desc : 

The app will calculate and keep a rolling total for each roll entry.

Logic : 

    The works on the concept of a frames : Open  and final frame. Each open frame would allow two rolls and the final frame would allow three rolls.
    Open frames if have no spares/strikes close after two rolls, if they have a spare then they close on the first roll of the subsequent frame, 
    else on strike it will only close after next two subsequent frames.
    Fianl frame is special and will allow an extra roll, on a spare or strike.
    Calculation done as a rolling total, to be displayed aftter each roll.( reduces iteration load, but api is flexible in case we have to do some analysis 
    on the stored data ).

Code Description :

     Application uses a PlayerContext for each player, per game. ( this is useful, in case we go for multiplayer game setup ).
                 A PlayerContext is equivalent to a bolwing lane ( exlusive to the player ).
                 
Main class : 

    BowlingScoreCalculator and supporting classes include PlayerContext and the Frames ( Open and Final frames extend from a parent Frame ).
    
Supporting classes:

    PlayerContext: Exclusive class for each player, created on game start. Holds data structures for Frames and calculation.
    
    Context : Parent context, right now juat the parent for PlayerContext, but in future if we want to have a GameContext.
    
    BolwingFrame : supporting class, which has utility to pick frames, this will be helpful in case we ever want to show the frames for the users to pick from.
    ( adds more strict validation on how user chooses to pick roll numbers ).
    
    OpenFrame : Frame Object for the each game play ( two rolls max ) each open frame.
    
    Final Frame : Fianl frame object for the game ( special, max three rolls ).
    
