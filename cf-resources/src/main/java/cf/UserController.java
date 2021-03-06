package cf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mongodb.*;

import java.util.*;

import cf.CfService;
import cf.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by abhishekupadhyay on 2016/02/16.
 */
@RestController
@RequestMapping(value="/urr")
class UserController {

    private final CfService cfService;


    @Autowired
    UserController(CfService cfService)
    {
        this.cfService=cfService;
    }


    @RequestMapping(value="/ur")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<DBObject> home() {

        System.out.println("yahan tak thik hai 0");

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(NewThreadConfig.class);
        ctx.refresh();
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ctx.getBean("taskExecutor");

        System.out.println("yahan tak thik hai 1");

        TestPrintTask tst = (TestPrintTask) ctx.getBean("testPrintTask");
        tst.setName("my print thread");
        taskExecutor.execute(tst);



        /*System.out.println("yahan tak thik hai 0");

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MailSenderConfig.class);
        ctx.refresh();
        JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        try {
            mailMsg.setFrom("abhishek@craftemporio.com");
            mailMsg.setTo("abhishek.upadhyay.cse12@iitbhu.ac.in");
            mailMsg.setSubject("Test mail From CraftEmporio");
            mailMsg.setText("Hello World!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        System.out.println("yahan tak thik hai 1");

        mailSender.send(mimeMessage);
        System.out.println("---Done---");*/



        DBObject dbobj = new BasicDBObject();
        List<DBObject> dblist= null;
        try {
            dblist = cfService.find(dbobj);
            System.out.println(dblist);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dblist;
    }
    }





