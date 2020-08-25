package com.sigruptor.datastructure.tree;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.PrintStream;

/**
 * @author abhishek_jhanwar on 2020-08-24
 * Email: abhishek_jhanwar@apple.com
 **/
public class TreeManager {

    private static final Logger logger = LogManager.getLogger(TreeManager.class);

    private static void initializeConsoleProxy() {
        System.setOut(createLoggingProxy(System.out, LogManager.getLogger("STDOUT")));
        System.setErr(createLoggingProxy(System.err, LogManager.getLogger("STDERR")));
    }

    private static PrintStream createLoggingProxy(final PrintStream realPrintStream, Logger logger) {
        return new PrintStream(realPrintStream) {
            @Override
            public void print(final String string) {
                realPrintStream.print(string);
                logger.info(string);
            }
        };
    }

    public static void main(String[] args) {
        initializeConsoleProxy();
        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "TRACE");
        logger.debug("Starting");
    }
}
