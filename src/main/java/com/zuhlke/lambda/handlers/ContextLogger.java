package com.zuhlke.lambda.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.io.InputStream;
import java.io.OutputStream;

public class ContextLogger {

    public static String myHandler(InputStream inputStream, OutputStream outputStream, Context context) {
        LambdaLogger logger = context.getLogger();
        int letter;
        try {
            while ((letter = inputStream.read()) != -1) {
                outputStream.write(Character.toUpperCase(letter));
            }
            Thread.sleep(3000); // Intentional delay for testing the getRemainingTimeInMillis() result.
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.log("Log data from LambdaLogger \n with multiple lines");
        // Print info from the context object
        logger.log("Function name: " + context.getFunctionName());
        logger.log("Max mem allocated: " + context.getMemoryLimitInMB());
        logger.log("Time remaining in milliseconds: " + context.getRemainingTimeInMillis());

        // Return the log stream name so you can look up the log later.
        return String.format("Hello %s. log stream = %s", "Asyl", context.getLogStreamName());

    }
}
