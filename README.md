# J2EE_school_project

## About


## Dependencies

- Docker
- Docker Compose
- Maven

## Installation

### How to install Docker & Docker Compose

#### Linux

- Red Hat et dérivés: `sudo yum install docker `
- Debian et dérivés: `sudo apt install docker.io`
- From the installation script: `curl −s https://get.docker.com/ | sudo sh`
- Install Docker Compose: `sudo apt install docker-compose`

#### Windows

- Download & install: https://docs.docker.com/desktop/install/windows-install/
- Run `wsl --update`

### How to build the containers


Run the following command in the root folder of this project : `docker-compose up -d`



#### If you get an error:

<ul> 
	<li>
		Check if you can execute <code>docker ps</code> without sudo. If not then do the following:
		<ol>
			<li><code>sudo groupadd docker</code></li>
			<li><code>sudo gpasswd -a $USER docker</code></li>
			<li><code>sudo service docker restart</code></li>
			<li><code>sudo chown $USER /var/run/docker.sock</code></li>
			<li>If docker-compose still doesn't work then try restarting your computer</li>
		</ol>
	</li>
	<li>
		If you can run <code>docker-compose up -d</code> but you get the error :<br>
		<code style="color:red">Error starting userland proxy: listen tcp4 0.0.0.0:3306: bind: address already in use</code><br>
		The port used by the container may already be used so:
		<ol>
			<li>Open parking-app/docker-compose.yml</li>
			<li>
				Change the port at the line <code>- "3307:3307"</code> in <code>ports</code> in <code>db</code> to another (<code>3308:3308</code> for instance)
			</li>
			<li>
				<code>docker-compose up -d</code>
			</li>
		</ol>
	</li>
</ul>


## Compile

Run `mvn clean package`

## Run

### Containers

Before opening the website the containers need to be running (with `docker-compose up -d`).
If you want to stop all the containers of this project, run `docker-compose down`

### To open the website

Open in a web browser: http://localhost:8082/J2EE_Project-1.0-SNAPSHOT/

### To edit the database (admin)

**(copied from one of my project, needs to be updated)**

1. Run <code>docker ps</code>
2. Get the CONTAINER ID (1st column) of the mysql IMAGE
3. Replace CONTAINER_ID with what you got in (2.), in <code>docker exec -ti CONTAINER_ID bash</code> and run it
4. Enter <code>mysql -p</code>
6. Enter the password that was defined in the environment variable MYSQL_ROOT_PASSWORD in docker-compose.yml
7. Enter <code>use usersdata;</code>
8. Enter the queries you want to do

## Authors

- Theo Gandy
- Robin Meneust
- Jeremy Saelen
- Lucas Velay