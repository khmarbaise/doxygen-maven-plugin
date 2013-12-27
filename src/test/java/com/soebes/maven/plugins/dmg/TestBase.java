/**
 * The Doxygen Maven Plugin (dmp)
 *
 * Copyright (c) 2010 - 2014 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
 * Copyright (c) 2010 - 2014 by Karl Heinz Marbaise
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soebes.maven.plugins.dmg;

import java.io.File;
import java.net.URL;

/**
 * This is a collection help methods which are 
 * often used in Unit Tests.
 * 
 * @author Karl Heinz Marbaise
 */
public class TestBase {
	/**
	 * This method will give you back
	 * the filename incl. the absolute path name
	 * to the resource. 
	 * If the resource does not exist it will give
	 * you back the resource name incl. the path.
	 * 
	 * It will give you back an absolute path
	 * incl. the name which is in the same directory 
	 * as the the class you've called it from.
	 * 
	 * @param name
	 * @return
	 */
	public String getFileResource(String name) {
		URL url = this.getClass().getResource(name);
		if (url != null) {
			return url.getFile();
		} else {
			//We have a file which does not exists
			//We got the path
			url = this.getClass().getResource(".");
			return url.getFile() + name;
		}
	}

	/**
	 * Return the base directory of the project.
	 * This works under Maven on command line and within Eclipse as well.
	 * @return The path with is defined by basedir.
	 */
	public String getMavenBaseDir() {
		return System.getProperty("basedir", System.getProperty("user.dir", "."));
	}

	/**
	 * Return the target folder of the current project.
	 * @return The target folder.
	 */
	public String getTargetDir() {
		return getMavenBaseDir() + File.separatorChar + "target";
	}

	/**
	 * This will return the src folder.
	 * @return The src folder of the project.
	 */
	public String getSrcDirectory () {
		return getMavenBaseDir() + File.separator + "src" ;
	}
	
	/**
	 * This will return the <code>src/test</code> folder.
	 * @return The src/test folder.
	 */
	public String getTestDirectory () {
		return getSrcDirectory() + File.separator + "test";
	}

	/**
	 * This will return the <code>src/test/resources</code> folder of the project.
	 * @return The src/test/resources folder.
	 */
	public String getTestResourcesDirectory() {
		return getTestDirectory() + File.separator + "resources";
	}
}
