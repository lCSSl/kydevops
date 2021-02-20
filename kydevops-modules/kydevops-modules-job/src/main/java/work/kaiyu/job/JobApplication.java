package work.kaiyu.job;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import work.kaiyu.common.security.annotation.EnableCustomConfig;
import work.kaiyu.common.security.annotation.EnableRyFeignClients;
import work.kaiyu.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 *
 * @author css
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
public class JobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(JobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
