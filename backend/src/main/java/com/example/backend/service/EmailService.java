package com.example.backend.service;

import com.example.backend.domain.Alert;
import com.example.backend.domain.Member;
import com.example.backend.domain.Threat;
import com.example.backend.repository.MemberRepository;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class EmailService {

    private final JavaMailSender mailSender;
    private final ExecutorService executorService;
    private final MemberRepository memberRepository;
    private static final Logger logger = LogManager.getLogger(RecordService.class);

    public EmailService(JavaMailSender mailSender, MemberRepository memberRepository) {
        this.mailSender = mailSender;
        this.memberRepository = memberRepository;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @SneakyThrows
    public void sendConfirmationEmail(Member securityUser, String token) {

        String recipientAddress = securityUser.getEmail();
        String subject = "Confirmation Email";
        String confirmationUrl = "https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/register/confirmEmail?token=" + token;

        String body = getBodyForConfirm(confirmationUrl);

        sendEmail(recipientAddress, subject, body);
    }


    @SneakyThrows
    public void sendCode(Member securityUser, String token) {
        String recipientAddress = securityUser.getEmail();
        String subject = "Login Email";
        String body = getBodyForCode(token);

        sendEmail(recipientAddress, subject, body);

    }

    @SneakyThrows
    public void sendAlert(Alert alert, List<Threat> threats) {
        String subject = "Alert Notification";
        String body = getBodyForAlert(alert, threats);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(body,true);
        helper.setSubject(subject);
        helper.setFrom("evil.evaluators@gmail.com");

        if(alert.isMinorAlert()){
            List<Member> minorMembers = memberRepository.findMembersByMinorAlertIsTrue();
            List<String> minorRecipientAddresses = minorMembers.stream().map(Member::getEmail).toList();
            for (String recipientAddress : minorRecipientAddresses) {
                helper.setTo(recipientAddress);
                executorService.execute(() -> mailSender.send(mimeMessage));
            }
        }
        else {
            List<Member> members = memberRepository.findAll();
            List<String> recipientAddresses = members.stream().map(Member::getEmail).toList();
            for (String recipientAddress : recipientAddresses) {
                helper.setTo(recipientAddress);
                executorService.execute(() -> mailSender.send(mimeMessage));
            }
        }
        logger.info("Alert sent to all users");
    }

    @SneakyThrows
    private void sendEmail(String recipientAddress, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(body,true);
        helper.setTo(recipientAddress);
        helper.setSubject(subject);
        helper.setFrom("evil.evaluators@gmail.com");
        mailSender.send(mimeMessage);
        logger.info("Email sent to " + recipientAddress);
    }

    private String getBodyForAlert(Alert alert, List<Threat> threats) {
        String description = alert.getDescription();
        String name = alert.getName();
        String html = "<html>\n" +
                "  <head>\n" +
                "    <title>Alert Notification</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        font-size: 14px;\n" +
                "        line-height: 1.5;\n" +
                "        color: #333;\n" +
                "      }\n" +
                "      h1 {\n" +
                "        font-size: 24px;\n" +
                "        font-weight: bold;\n" +
                "        margin-bottom: 20px;\n" +
                "      }\n" +
                "      table {\n" +
                "        width: 100%;\n" +
                "        border-collapse: collapse;\n" +
                "        margin-bottom: 20px;\n" +
                "      }\n" +
                "      th, td {\n" +
                "        padding: 8px;\n" +
                "        text-align: left;\n" +
                "        border: 1px solid #ddd;\n" +
                "      }\n" +
                "      th {\n" +
                "        background-color: #f2f2f2;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h1>Alert Notification</h1>\n" +
                "    <p><strong>Alert Name:</strong> " + name + "</p>\n" +
                "    <p><strong>Description:</strong> " + description + "</p>\n" +
                "    <table>\n" +
                "      <thead>\n" +
                "        <tr>\n" +
                "          <th>Threat Name</th>\n" +
                "          <th>Severity</th>\n" +
                "          <th>Source</th>\n" +
                "          <th>Device Type</th>\n" +
                "          <th>Potential Impact</th>\n" +
                "        </tr>\n" +
                "      </thead>\n" +
                "      <tbody>\n";

        for (Threat threat : threats) {
            html += "        <tr>\n" +
                    "          <td>" + threat.getName() + "</td>\n" +
                    "          <td>" + threat.getSeverity() + "</td>\n" +
                    "          <td>" + threat.getSource() + "</td>\n" +
                    "          <td>" + threat.getDeviceType() + "</td>\n" +
                    "          <td>" + threat.getPotentialImpact() + "</td>\n" +
                    "        </tr>\n";
        }

        html += "      </tbody>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        System.out.println(html);


        return html;
    }

    private String getBodyForConfirm(String confirmationURL){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                "  <title>Email Confirmation</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <style type=\"text/css\">\n" +
                "  /**\n" +
                "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n" +
                "   */\n" +
                "  @media screen {\n" +
                "    @font-face {\n" +
                "      font-family: 'Source Sans Pro';\n" +
                "      font-style: normal;\n" +
                "      font-weight: 400;\n" +
                "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n" +
                "    }\n" +
                "    @font-face {\n" +
                "      font-family: 'Source Sans Pro';\n" +
                "      font-style: normal;\n" +
                "      font-weight: 700;\n" +
                "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n" +
                "    }\n" +
                "  }\n" +
                "  /**\n" +
                "   * Avoid browser level font resizing.\n" +
                "   * 1. Windows Mobile\n" +
                "   * 2. iOS / OSX\n" +
                "   */\n" +
                "  body,\n" +
                "  table,\n" +
                "  td,\n" +
                "  a {\n" +
                "    -ms-text-size-adjust: 100%; /* 1 */\n" +
                "    -webkit-text-size-adjust: 100%; /* 2 */\n" +
                "    text-decoration: none;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Remove extra space added to tables and cells in Outlook.\n" +
                "   */\n" +
                "  table,\n" +
                "  td {\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    mso-table-lspace: 0pt;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Better fluid images in Internet Explorer.\n" +
                "   */\n" +
                "  img {\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Remove blue links for iOS devices.\n" +
                "   */\n" +
                "  a[x-apple-data-detectors] {\n" +
                "    font-family: inherit !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Fix centering issues in Android 4.4.\n" +
                "   */\n" +
                "  div[style*=\"margin: 16px 0;\"] {\n" +
                "    margin: 0 !important;\n" +
                "  }\n" +
                "  body {\n" +
                "    width: 100% !important;\n" +
                "    height: 100% !important;\n" +
                "    padding: 0 !important;\n" +
                "    margin: 0 !important;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Collapse table borders to avoid space between cells.\n" +
                "   */\n" +
                "  table {\n" +
                "    border-collapse: collapse !important;\n" +
                "  }\n" +
                "  a {\n" +
                "    color: #1a82e2;\n" +
                "  }\n" +
                "  img {\n" +
                "    height: auto;\n" +
                "    line-height: 100%;\n" +
                "    text-decoration: none;\n" +
                "    border: 0;\n" +
                "    outline: none;\n" +
                "  }\n" +
                "  </style>\n" +
                "\n" +
                "</head>\n" +
                "<body style=\"background-color: #e9ecef;\">\n" +
                "\n" +
                "  <!-- start preheader -->\n" +
                "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n" +
                "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
                "  </div>\n" +
                "  <!-- end preheader -->\n" +
                "\n" +
                "  <!-- start body -->\n" +
                "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "\n" +
                "    <!-- start logo -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "          <tr>\n" +
                "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
                "              <a href=\"https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io\" target=\"_blank\" style=\"display: inline-block;\">\n" +
                "               <span style=\"background-color: #000; color: #fff; padding: 10px; font-size: 30px; font-weight: bold; font-family: Arial, sans-serif; text-align: center;\">\n" +
                "                   Evil <span style=\"color: #fff;\">Eval()</span>uators\n" +
                "                   </span>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end logo -->\n" +
                "\n" +
                "    <!-- start hero -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Confirm Your E-mail Address</h1>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end hero -->\n" +
                "\n" +
                "    <!-- start copy block -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "\n" +
                "          <!-- start copy -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "              <p style=\"margin: 0;\">Tap the button below to confirm your email address. If you didn't create an account with <a>Evil Eval()uators</a>, you can safely delete this email.</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end copy -->\n" +
                "\n" +
                "          <!-- start button -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                <tr>\n" +
                "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n" +
                "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                      <tr>\n" +
                "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\n" +
                "                          <a href=\"" + confirmationURL + "\" target=\"_blank\" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">Confirm your mail</a>\n" +
                "                        </td>\n" +
                "                      </tr>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end button -->\n" +
                "\n" +
                "          <!-- start copy -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "              <p style=\"margin: 0;\">Thanks for registering! Please click the button above to confirm your e-mail address.</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end copy -->\n" +
                "\n" +
                "          <!-- start copy -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n" +
                "              <p style=\"margin: 0;\">Cheers,<br> Evil Eval()uators</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end copy -->\n" +
                "\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end copy block -->\n" +
                "\n" +
                "    <!-- start footer -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "\n" +
                "          <!-- start permission -->\n" +
                "          <tr>\n" +
                "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                "              <p style=\"margin: 0;\">You received this e-mail because we received a registration request for your account. If you didn't request registration you can safely delete this email.</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end permission -->\n" +
                "\n" +
                "          <!-- start unsubscribe -->\n" +
                "          <tr>\n" +
                "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                "              <p style=\"margin: 0;\">FrontEd Workshop Hackathon @ Span</p>\n" +
                "              <p style=\"margin: 0;\">Evil Eval()uators, Zagreb, 2023</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end unsubscribe -->\n" +
                "\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end footer -->\n" +
                "\n" +
                "  </table>\n" +
                "  <!-- end body -->\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }


    private String getBodyForCode(String token){
        return "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Two Factor Authentication Code</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        font-size: 16px;\n" +
                "        line-height: 1.5;\n" +
                "      }\n" +
                "      \n" +
                "      .container {\n" +
                "        max-width: 600px;\n" +
                "        margin: 0 auto;\n" +
                "        padding: 20px;\n" +
                "        border: 1px solid #ccc;\n" +
                "      }\n" +
                "      \n" +
                "      h1 {\n" +
                "        margin-top: 0;\n" +
                "      }\n" +
                "      \n" +
                "      p {\n" +
                "        margin-bottom: 20px;\n" +
                "      }\n" +
                "      \n" +
                "      .code {\n" +
                "        display: inline-block;\n" +
                "        font-size: 24px;\n" +
                "        font-weight: bold;\n" +
                "        padding: 10px 20px;\n" +
                "        background-color: #f5f5f5;\n" +
                "        border: 1px solid #ccc;\n" +
                "        border-radius: 4px;\n" +
                "        margin-right: 10px;\n" +
                "      }\n" +
                "      \n" +
                "      .note {\n" +
                "        font-size: 14px;\n" +
                "        color: #999;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"container\">\n" +
                "      <h1>Two Factor Authentication Code</h1>\n" +
                "      <p>Please use the following code to complete your login:</p>\n" +
                "      <div>\n" +
                "        <span class=\"code\">" + token + "</span>\n" +
                "        <span class=\"note\">This code will expire in 5 minutes.</span>\n" +
                "      </div>\n" +
                "      <p>If you did not request this code, please ignore this email.</p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

}
