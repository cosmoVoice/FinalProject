package Blog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BlogPage {
    private WebDriver driver;

    public BlogPage(WebDriver driver) {
        this.driver = driver;
    }


    public BlogPage logIn (String loginField, String passwordField, String login, String password, String loginButton) {
        driver
                .findElement(By.xpath(loginField))
                .sendKeys(login);
        driver
                .findElement(By.xpath(passwordField))
                .sendKeys(password);
        driver
                .findElement(By.xpath(loginButton))
                .click();
        return this;
    }
    public BlogPage buttonClick (String button) {
        driver
                .findElement(By.xpath(button))
                .click();
        return this;
    }
    public BlogPage newPostForm (String titleField,
                                 String title,
                                 String descriptionField,
                                 String description,
                                 String contentField,
                                 String content) {
        driver
                .findElement(By.xpath(titleField))
                .sendKeys(title);
        driver
                .findElement(By.xpath(descriptionField))
                .sendKeys(description);
        driver
                .findElement(By.xpath(contentField))
                .sendKeys(content);
        return this;
    }
}


