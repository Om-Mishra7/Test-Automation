import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Timer;
import java.util.TimerTask;

public class File_1 {

    static WebDriver driver;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C://Users//Om Mishra//Downloads//chromedriver-win64//chromedriver-win64//chromedriver.exe");

        driver = new ChromeDriver();

        loginToWebsite();
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        followUser("om_mishra");
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        likeAndCommentPost("https://flutterbird.om-mishra.com/user/om_mishra/status/e92e3a75c70b03c31e9e190d7804ea47", "Nice post 🥳!");

        schedulePost("Hello World, Automated Message By Selenium", "15:34");
        
        try {
            // Wait indefinitely, allowing scheduled message to be sent
            synchronized (File_1.class) {
                File_1.class.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        driver.quit();
    }

    public static void loginToWebsite() {
        driver.get("https://flutterbird.om-mishra.com/authentication/sign-in");
        driver.manage().window().maximize();

        WebElement userIdentifier = driver.findElement(By.id("user_identifier"));
        WebElement userPassword = driver.findElement(By.id("user_password"));
        WebElement signInButton = driver.findElement(By.id("sign_in_button"));

        userIdentifier.sendKeys("flutterbird");
        userPassword.sendKeys("123456");

        signInButton.sendKeys(Keys.RETURN);

        try {
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void followUser(String username) {
        driver.get("https://flutterbird.om-mishra.com/user/" + username);

        try {
            WebElement followButton = driver.findElement(By.className("user-profile-follow-button"));
            Thread.sleep(2000);
            followButton.click();
        } catch (Exception e) {
            System.out.println("Error following user: " + e.getMessage());
        }
    }

    public static void likeAndCommentPost(String postUrl, String commentText) {
        driver.get(postUrl);

        try {
            WebElement likeButton = driver.findElement(By.xpath("/html/body/main/div[3]/div[2]/div/div/div[3]/div[1]"));
            likeButton.click();

            WebElement commentInput = driver.findElement(By.id("reply-content")); 
            commentInput.sendKeys(commentText);
            
            Thread.sleep(2000);
      
            WebElement postComment = driver.findElement(By.id("post-reply-button"));
            postComment.click();
        } catch (Exception e) {
            System.out.println("Error liking/commenting post: " + e.getMessage());
        }
    }

    public static void schedulePost(String postText, String postTime) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                postTweet(postText);
            }
        };

        long delay = calculateDelay(postTime);
        timer.schedule(task, delay);
    }

    public static void postTweet(String postText) {
    	    	
        driver.get("https://flutterbird.om-mishra.com/home");

        WebElement tweetContent = driver.findElement(By.id("tweet-content-homepage"));
        WebElement postTweetButton = driver.findElement(By.id("post-tweet-button"));

        tweetContent.sendKeys(postText);

        postTweetButton.sendKeys(Keys.RETURN);
    }

    public static long calculateDelay(String postTime) {
        String[] timeParts = postTime.split(":");
        int targetHour = Integer.parseInt(timeParts[0]);
        int targetMinute = Integer.parseInt(timeParts[1]);

        long currentTimeMillis = System.currentTimeMillis();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, targetHour);
        calendar.set(java.util.Calendar.MINUTE, targetMinute);
        calendar.set(java.util.Calendar.SECOND, 0);

        long targetTimeMillis = calendar.getTimeInMillis();

        if (targetTimeMillis < currentTimeMillis) {
            targetTimeMillis += 24 * 60 * 60 * 1000; 
        }

        return targetTimeMillis - currentTimeMillis;
    }
}
