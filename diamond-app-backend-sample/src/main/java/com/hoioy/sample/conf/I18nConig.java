package com.hoioy.sample.conf;//package com.hoioy.diamond.conf;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//
//import java.util.Locale;
//
///**
// * i18n拦截器
// */
//@Configuration
//public class I18nConig {
//
//    /**
//     * 国际化，设置默认的语言为中文
//     * 将用户的区域信息存在session
//     * 也可以存在cookie,由客户端保存   {@link org.springframework.web.servlet.i18n.CookieLocaleResolver}
//     *
//     * @return
//     */
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
//        return slr;
//    }
//
//    /**
//     * 国际化，设置url识别参数
//     *
//     * @return
//     */
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        lci.setParamName("lang");
//        return lci;
//    }
//}