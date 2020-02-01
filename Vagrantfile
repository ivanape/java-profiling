$script = <<-SCRIPT
    echo Install required software... 
    sudo yum -y groupinstall "Development Tools" "Development Libraries"
    sudo rpm -U /vagrant/temp/jdk-8u241-linux-x64.rpm
    sudo yum -y install maven git
    sudo yum -y install qt-devel cmake
    sudo dnf -y install perf
    sudo dnf install -y perl-open.noarch

    echo Set Environtment...
    echo "export JAVA_HOME=/usr/java/jdk1.8.0_241-amd64" >> /home/vagrant/.bashrc
    echo "export PERF_RECORD_SECONDS=120" >> /home/vagrant/.bashrc
    echo "export FLAMEGRAPH_DIR=/vagrant/src/FlameGraph" >> /home/vagrant/.bashrc
    echo "export PATH=$JAVA_HOME/bin:$PATH" >> /home/vagrant/.bashrc
    source /home/vagrant/.bashrc

    mkdir -p /vagrant/src
    echo Clone Repositories...
    cd /vagrant/src
    git clone https://github.com/brendangregg/FlameGraph

    cd /vagrant/src
    git clone https://github.com/jvm-profiling-tools/perf-map-agent
    sudo chown -R vagrant:vagrant perf-map-agent/
    cd perf-map-agent
    cmake .
    make
    git clone https://github.com/brendangregg/FlameGraph
    
    cd ..
    git clone https://github.com/saquibkhan/javaFlameGraph
    cd javaFlameGraph
    git clone https://github.com/brendangregg/FlameGraph

    cd ..
    git clone https://github.com/lhotari/jfr-report-tool
    cd jfr-report-tool
    ./gradlew shadowJar

    date > /etc/vagrant_provisioned_at    
SCRIPT

Vagrant.configure("2") do |config| 
    config.vm.box = "bento/fedora-31"
    config.vm.box_version = "201912.14.0"

    config.vm.network "private_network", ip: "192.168.50.4"
    config.vm.network "forwarded_port", guest: 9090, host: 9090
    config.vm.network "forwarded_port", guest: 8080, host: 8080
    config.vm.network "forwarded_port", guest: 8070, host: 8070
    config.vm.network "forwarded_port", guest: 8060, host: 8060

    config.vm.provider "virtualbox" do |v|
        v.name = "JAVA Profiling"
        v.memory = 2048
        v.cpus = 4
    end

    config.vm.provision "shell", inline: $script
    config.vm.provision :reload
end
