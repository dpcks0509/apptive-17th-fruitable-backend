package apptive.fruitable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@EnableJpaAuditing
public class FruitableApplication {

    static {
        System.setProperty("com.amazone.sdk.disableEc2Metadata", "true");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FruitableApplication.class, args);
    }

    /*
     * @PutMapping 과 @DeleteMapping 작동할 수 있도록 해줌
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}

