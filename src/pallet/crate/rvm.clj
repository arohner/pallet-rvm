(ns pallet.crate.rvm
  "Installation of rvm from source"
  (:require
   [pallet.action.exec-script :as exec-script]
   [pallet.action.package :as package]
   [pallet.session :as session]
   [pallet.stevedore :as stevedore]
   [pallet.thread-expr :as thread-expr]))

(def dependencies
  {:aptitude ["git-core"
              "curl" ;; dependencies for rvm itself.

              ;;; dependencies to build new versions of ruby
              "autoconf"
              "automake"
              "bison"
              "bzip2"
              "build-essential"
              "curl"
              "g++"
              "git-core"
              "libc6-dev"
              "libreadline6"
              "libreadline6-dev"
              "libsqlite3-0"
              "libsqlite3-dev"
              "libssl-dev"
              "libtool"
              "libxml2-dev"
              "libxslt-dev"
              "libyaml-dev"
              "ncurses-dev"
              "make"
              "openssl"
              "sqlite3"
              "subversion"
              "zlib1g"
              "zlib1g-dev"

              ;;; dependencies for new jruby
              "ant"
              ;;; some form of java, not listed here
              ]})

(defn rvm  [session]
  "install rvm, as the limited user"
  (-> session
      (package/packages :aptitude (-> dependencies :aptitude))
      (exec-script/exec-checked-script
       "Installing RVM"
       (sudo "-i" "-u" ~(-> session :user :username) "bash" "< <(curl -s https://rvm.beginrescueend.com/install/rvm)"))
      (exec-script/exec-checked-script
       "Installing RVM source fn to .bash_profile"
       (sudo "-i" "-u" ~(-> session :user :username) "bash" "-c" "\"echo '[[ -s \"$HOME/.rvm/scripts/rvm\" ]] && . \"$HOME/.rvm/scripts/rvm\" # Load RVM function' >> ~/.bash_profile\""))))