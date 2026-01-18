package com.automation.utils;

import com.automation.config.ConfigReader;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * EmailUtil class to send test execution reports via email
 *
 * @author Sid
 * @version 1.0
 */
public class EmailUtil {

    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Send email with test report attachment
     * @param reportPath Path to the HTML report
     * @return true if email sent successfully, false otherwise
     */
    public static boolean sendEmailWithReport(String reportPath) {

        // Check if email is enabled
        if (!isEmailEnabled()) {
            System.out.println("Email reporting is disabled in config.properties");
            return false;
        }

        try {
            // Get email configuration
            String host = config.getProperty("email.smtp.host");
            String port = config.getProperty("email.smtp.port");
            String username = config.getProperty("email.username");
            String password = config.getProperty("email.password");
            String recipients = config.getProperty("email.recipients");
            String subject = config.getProperty("email.subject");

            // Setup mail server properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.ssl.trust", host);

            // Create session with authentication
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            // Add recipients
            String[] recipientList = recipients.split(",");
            InternetAddress[] recipientAddresses = new InternetAddress[recipientList.length];
            for (int i = 0; i < recipientList.length; i++) {
                recipientAddresses[i] = new InternetAddress(recipientList[i].trim());
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);

            // Set subject with timestamp
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            message.setSubject(subject + " - " + timestamp);

            // Create message body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            String emailBody = createEmailBody();
            messageBodyPart.setContent(emailBody, "text/html");

            // Create attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File reportFile = new File(reportPath);
            if (reportFile.exists()) {
                DataSource source = new FileDataSource(reportFile);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(reportFile.getName());
            }

            // Combine body and attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (reportFile.exists()) {
                multipart.addBodyPart(attachmentPart);
            }

            // Set content
            message.setContent(multipart);

            // Send email
            Transport.send(message);

            System.out.println("✅ Email sent successfully to: " + recipients);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create HTML email body
     * @return HTML email body
     */
    private static String createEmailBody() {
        String timestamp = new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' HH:mm:ss").format(new Date());

        return "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<div style='background-color: #f4f4f4; padding: 20px;'>" +
                "<div style='background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);'>" +
                "<h2 style='color: #2c3e50; border-bottom: 3px solid #3498db; padding-bottom: 10px;'>" +
                "🚀 Test Execution Report</h2>" +
                "<p style='font-size: 14px; color: #555;'>" +
                "<strong>Execution Time:</strong> " + timestamp + "</p>" +
                "<p style='font-size: 14px; color: #555;'>" +
                "<strong>Application:</strong> " + config.getProperty("app.name", "React Shopping Cart") + "</p>" +
                "<p style='font-size: 14px; color: #555;'>" +
                "<strong>Environment:</strong> QA</p>" +
                "<div style='margin: 20px 0; padding: 15px; background-color: #ecf0f1; border-left: 4px solid #3498db;'>" +
                "<p style='margin: 0; color: #2c3e50;'>" +
                "📊 <strong>Test execution completed successfully!</strong></p>" +
                "<p style='margin: 10px 0 0 0; color: #555;'>" +
                "Please find the detailed test execution report attached to this email.</p>" +
                "</div>" +
                "<p style='font-size: 14px; color: #555; margin-top: 20px;'>" +
                "<strong>Report Details:</strong></p>" +
                "<ul style='color: #555;'>" +
                "<li>Framework: Selenium + TestNG + Page Object Model</li>" +
                "<li>Browser: " + config.getBrowser() + "</li>" +
                "<li>Report Format: HTML (ExtentReports)</li>" +
                "</ul>" +
                "<div style='margin-top: 30px; padding-top: 20px; border-top: 1px solid #ddd;'>" +
                "<p style='font-size: 12px; color: #999;'>" +
                "This is an automated email from the Selenium Test Automation Framework. Please do not reply.</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Check if email is enabled in configuration
     * @return true if enabled, false otherwise
     */
    private static boolean isEmailEnabled() {
        try {
            return Boolean.parseBoolean(config.getProperty("email.enabled", "false"));
        } catch (Exception e) {
            return false;
        }
    }
}