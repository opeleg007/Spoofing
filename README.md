# Spoofing

Source code to determine if a url is attempting a 'DNS Spoofing' attack on a list of valid urls.

## Metrics Used

1. Edit Distance
2. Common Characters

### Proccess
1. if the url is valid and exists in the list of valid urls - Good to go. 
2. calculate edit distance 
    *The number of changes that exist between two strings
3. calculate Uncommon chars
    *The number of characters the are uncommon between two strings
4. the valid url with the lowest combined score, it most likely the on that has been spoofed

##### Returned Value
a custom Pair with (String, String)
  1. if no spooging - (url, "No Spoofing")
  2. if spoofing - (url, "Spoofing")
