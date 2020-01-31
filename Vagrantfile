$script = <<-SCRIPT
    sudo rpm -U /vagrant/temp/jdk-8u241-linux-x64.rpm
    sudo dnf -y install perf
    sudo yum -y install kernel-devel kernel-headers
    sudo yum -y install maven git
    sudo yum -y install qt-devel cmake
    sudo dnf install -y perl-open.noarch

    mkdir -p /vagrant/src
    echo Clone Repositories...
    cd /vagrant/src
    git clone https://github.com/brendangregg/FlameGraph

    echo Set Environtment...
    echo "export JAVA_HOME=/usr/java/jdk1.8.0_241-amd64" >> /home/vagrant/.bashrc
    echo "export PERF_RECORD_SECONDS=120" >> /home/vagrant/.bashrc
    echo "export FLAMEGRAPH_DIR=/vagrant/src/FlameGraph" >> /home/vagrant/.bashrc
    echo "PATH=$PATH:$JAVA_HOME/bin" >> /home/vagrant/.bashrc
    export "PATH" >> /home/vagrant/.bashrc
    source /home/vagrant/.bashrc

    cd /vagrant/src
    git clone https://github.com/jvm-profiling-tools/perf-map-agent
    sudo chown -R vagrant:vagrant perf-map-agent/
    cd perf-map-agent
    cmake .
    make
    git clone https://github.com/brendangregg/FlameGraph
    
    cd ..
    git clone https://github.com/jvm-profiling-tools/async-profiler
    sudo chown -R vagrant:vagrant async-profiler/
    cd async-profiler
    make

    cd ..
    git clone https://github.com/saquibkhan/javaFlameGraph
    cd javaFlameGraph
    git clone https://github.com/brendangregg/FlameGraph

    git clone https://github.com/lhotari/jfr-report-tool
    cd jfr-report-tool
    ./gradlew shadowJar

    date > /etc/vagrant_provisioned_at    
SCRIPT

Vagrant.configure("2") do |config| 
    config.vm.box = "bento/fedora-31"
    config.vm.box_version = "201912.14.0"

    config.vm.network "private_network", ip: "192.168.50.4"
    config.vm.network "forwarded_port", guest: 44323, host: 44323
    config.vm.network "forwarded_port", guest: 44322, host: 44322
    config.vm.network "forwarded_port", guest: 8080, host: 8080
    config.vm.network "forwarded_port", guest: 8080, host: 8080
    config.vm.network "forwarded_port", guest: 8070, host: 8070
    config.vm.network "forwarded_port", guest: 8060, host: 8060
    config.vm.network "forwarded_port", guest: 8050, host: 8050
    config.vm.network "forwarded_port", guest: 3000, host: 3000
    config.vm.network "forwarded_port", guest: 9090, host: 9090

    config.vm.provider "virtualbox" do |v|
        v.name = "JAVA Profiling"
        v.memory = 2048
        v.cpus = 4
    end

    config.vm.provision "shell", inline: $script
end

# INSTALL OpenJDK8
#install java-1.8.0-openjdk-devel
#sudo yum -y groupinstall "Development Tools" "Development Libraries"


# INSTALL OracleJDK (you must first download RPM on temp dir)
#sudo dnf install jdk-8u241-linux-x64.rpm

#INSTALL PCP Grafana
#sudo yum -y install pcp
#sudo systemctl enable pmcd
#sudo systemctl enable pmlogger
#sudo systemctl start pmcd
#sudo dnf -y install grafana-pcp
#sudo systemctl restart grafana-server
#sudo systemctl start pmproxy
#sudo dnf -y install redis
#sudo systemctl start redis pmlogger pmproxy
#sudo dnf -y install pcp-pmda-bpftrace
#cd /var/lib/pcp/pmdas/bpftrace
#sudo ./Install