/*
 * Copyright 2014-present Deezer.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.deezer.research.language;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * Simple class to start the service.
 *
 * N.B.: As this classs does not hold any references to this project, it could be extracted and be
 * provided as a dependecy.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class DetectionConfiguration {
    public static void main(String[] args) {
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("spring.profiles.active", "both");
        SpringApplication app = new SpringApplication(DetectionConfiguration.class);
        app.setDefaultProperties(defaults);
        app.run(args);
    }
}
