Xvfb :99 &
export DISPLAY=:99

cd  /home/hancel

while [ 1 ]; 
do   
  python3 linphone_bot.py
done


