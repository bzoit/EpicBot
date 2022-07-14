package wt;
import robocode.*;
import java.awt.Color;
import robocode.util.*;
import java.awt.geom.*;
import robocode.WinEvent;
import robocode.ScannedRobotEvent;
import robocode.AdvancedRobot;
import robocode.*;
import robocode.util.*;
import java.awt.*;


// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * EpicBot - a robot by Wilson Theobald
 */
public class EpicBot extends AdvancedRobot {

    static String enemyLog = "000000000000000000000000000000888888888888888765432100888765432101234567888765432100"; //The list where enemy lateral velocities are stored

    static final double FIREPOWER = 2; //The firepower of the bot
    static final double BULLETVEL = 20-3*FIREPOWER; //The velocity of the bullet fired at FIREPOWER
    static final int PATTERN_DEPTH = 40; //The length of the pattern we try to find each turn in enemyLog
    static final double MOVEAMOUNT = 37.0; //The amount of pixels the bot will move in stop and go

    static double prevEnergy; //It stores the energy of the enemy
    static double direction = MOVEAMOUNT; //It's the direction and the movement packed together

	public void run() {

		setColors(Color.blue,Color.white,Color.green); // body,gun,radar

		while(true) {
			turnRadarRightRadians(Double.POSITIVE_INFINITY);
		}
	}

    public void onScannedRobot(ScannedRobotEvent e){

   		int matchLength = PATTERN_DEPTH; 
    	double absB;
    	int i; 		 		    		
    	int index;                        

        if(prevEnergy > (prevEnergy = e.getEnergy()) ){
        	direction = (Math.random()*100000-50000);
            setAhead(direction);
        }
		
        setTurnRightRadians((Math.cos(absB = e.getBearingRadians())));
			
        enemyLog = String.valueOf( (char)Math.round(e.getVelocity() * Math.sin(e.getHeadingRadians() - ( absB+=getHeadingRadians() )))).concat(enemyLog);

        while((index = enemyLog.indexOf(enemyLog.substring(0, matchLength--), (i = (int)((e.getDistance())/BULLETVEL)))) < 0);

        do {
        	absB += Math.asin(((byte)enemyLog.charAt(index--))/e.getDistance());
        } while(--i > 0);

        setTurnGunRightRadians(Utils.normalRelativeAngle(absB-getGunHeadingRadians()));
            
        setFire(FIREPOWER);

        setTurnRadarLeft(getRadarTurnRemaining());
    }

	public void onHitByBullet(HitByBulletEvent e) {
	}
	
	public void onHitWall(HitWallEvent e) {
		direction = -direction;
	}	
	
	public void onWin(WinEvent w) {
		for (int i = 0; i < 150; i++) {
			turnRight(50);
			ahead(50);
			turnLeft(50);
		}
	}
}
