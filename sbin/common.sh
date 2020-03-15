function error {
    echo -e "\e[31m[EROR]\e[0m $1"
    exit 1
}

function info {
  echo -e "\e[32m[INFO]\e[0m $1"
}