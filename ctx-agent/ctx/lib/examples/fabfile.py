"""
A Fabric file that aids in release needed
supporting code when using Chef and the
Nimbus content Agent/Broker.

TODO: Generalize the file.
"""
from fabric.api import env, run, local, put


env.user = ""
env.hosts = [""]

def ctxagent():
    local("cd ../nimbus; git archive --format=tar --prefix=nimbus/ HEAD:ctx-agent | gzip > /tmp/nimbus_ctx-agent.tar.gz")
    put("/tmp/nimbus_ctx-agent.tar.gz", "~/cpe/ctxagent")

def cookbooks():
    local("cd ../ooi-cookbooks; git archive --format=tar --prefix=cookbooks/ HEAD | gzip > /tmp/cookbooks.tar.gz")
    put("/tmp/cookbooks.tar.gz", "~/cpe/ctxagent/chef")
    run("cd ~/cpe/ctxagent/chef && tar -zxf cookbooks.tar.gz")

def release():
    run("rm -rf ~/cpe/ctxagent/* && mkdir ~/cpe/ctxagent/chef") 
    put("../ooi-cookbooks/config.json", "~/cpe/ctxagent/chef")
    ctxagent()
    cookbooks()
    run("cd ~/cpe/ctxagent && tar -cf chef.tar chef && gzip chef.tar")


########################################################################################################################


def bootstrap():
    run("apt-get -q update && apt-get -q upgrade -y")
    #install chef-solo ond ohai:
    run("apt-get install -y ruby-dev rubygems")
    run("gem install chef ohai --no-ri --no-rdoc --source http://gems.opscode.com --source http://gems.rubyforge.org")
    run("ln -s /var/lib/gems/1.8/bin/chef-solo /usr/local/bin/")
    run("ln -s /var/lib/gems/1.8/bin/ohai /usr/local/bin/")

def p():
    put("solo.rb", "/tmp/solo.rb")
    put("attrs.rb", "/tmp/attrs.rb")

def ohai():
    run("ohai")

