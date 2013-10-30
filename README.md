jax-rs-on-karaf
===============

Simple Karaf Launchpad wrapping JAX-RS feature with a JAX-RS publisher and Swagger.

#### Building JAX-RS publisher and a JAX-RS sample project

1. Build a sample of a JAX-RS publisher for OSGI. Its purpose is to track JAX-RS resources that get added or removed and make them available through Jersey.
`https://github.com/ddragosd/osgi-jax-rs-connector/tree/master/bundles/com.eclipsesource.jaxrs.publisher`. 
This is the bundle responsible to publish any JAX-RS resources.
2. Build jaxrs-example. This contains a simple JAX-RS resource to play with.
```
  cd ./jax-rs-example/
  mvn clean install 
```
3. Build Swagger for OSGI. Until issue [#272](https://github.com/wordnik/swagger-core/issues/272) is closed you need to build the bundles from my fork located at: `https://github.com/ddragosd/swagger-core/tree/scala_2.10.0`
4. To be added: `swagger-OSGI-connector` bundle. Its purpose is to reset Swagger cache every time a new Jax-RS resource is published.

#### Building the Apache Karaf Launchpad

```
  cd ./karaf-launchpad/
  mvn clean install
```

#### Running the Launchpad
After building successfully the launchpad, the distribution is located at `./karaf-launchpad/launchpad/target`.
Go inside the directory and unzip the `tar.gz` for *nix systems, or the `bat` file for Windows systems.
To run the launchpad you start it as any other Karaf instances. The complete list of commands to unpack and run on a linux system is:
```
cd ./karaf-launchpad/launchpad/target/
tar -xvf ./launchpad-*.tar.gz
./launchpad-0.1.0-SNAPSHOT/bin/karaf
# you should see the Apache Karaf console as shown bellow
        __ __                  ____      
       / //_/____ __________ _/ __/      
      / ,<  / __ `/ ___/ __ `/ /_        
     / /| |/ /_/ / /  / /_/ / __/        
    /_/ |_|\__,_/_/   \__,_/_/         

  Apache Karaf (2.3.2)

Hit '<tab>' for a list of available commands
and '[cmd] --help' for help on a specific command.
Hit '<ctrl-d>' or type 'osgi:shutdown' or 'logout' to shutdown Karaf.

karaf@root>
```

#### Playing with JAX-RS samples
To view the JAX-RS samples in action simply open a browser to `http://localhost:8181/api/hello` 
The source code for the resource that displays the message is found at : `https://github.com/ddragosd/jax-rs-on-karaf/blob/master/jax-rs-example/src/main/java/jaxrs/example/hello/HelloWorldResource.java`.


#### DISCALIMER 
This is not production ready code. I've published it in order to demonstrate how easy it is to get started with JAX-RS in the OSGI world. 
Feel free to use it as-is without any warranty and adapt it to your own needs. The integration-tests should provide you a solid foundation to shape it for your requirements. 

#### License
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
