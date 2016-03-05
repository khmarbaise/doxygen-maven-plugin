# Doxygen Maven Plugin

[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/khmarbaise/doxygen-maven-plugin.svg?label=License)](http://www.apache.org/licenses/)
[![Build Status](https://travis-ci.org/khmarbaise/doxygen-maven-plugin.svg?branch=master)](https://travis-ci.org/khmarbaise/doxygen-maven-plugin)
[![Maven Central](https://img.shields.io/maven-central/v/com.soebes.maven.plugins/doxygen-maven-plugin.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22doxygen-maven-plugin%22%20g%3A%22com.soebes.maven.plugins%22)

## License

[Apache License, Version 2.0, January 2004](http://www.apache.org/licenses/)

## Issue Tracker

[The Issue Tracker](https://github.com/khmarbaise/doxygen-maven-plugin/issues)

## Status


## TODOs


## Usage

The first and simplest usage is to configure the Maven Licenses Verifier Plugin

    <plugin>
      <groupId>com.soebes.maven.plugins</groupId>
      <artifactId>doxygen-maven-plugin</artifactId>
      <version>1.1.0</version>
      <executions>
        <execution>
          <phase>test</phase>
          <goals>
            <goal>check</goal>
          </goals>
        </execution>
      </executions>
    </plugin>

## Settings Configuration

If you like you can configure an appropriate plugin group in your
settings.xml file to make life a little bit easier.

    <settings>
      ...
      <pluginGroups>
        <pluginGroup>com.soebes.maven.plugins</pluginGroup>
      </pluginGroups>
      ...
    </settings>

