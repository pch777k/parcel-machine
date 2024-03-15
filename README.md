# parcel-machine

A simple console application for sending and receiving parcels from a parcel machine. The parcel has three possible sizes (small, medium, large). The parcel machine has a specific number of spaces for each size. Once the parcel has been sent, a code is generated to enable its collection. Receiving a parcel is done by entering the parcel number and the receive code. Additional functions include displaying all parcels and searching for a parcel by number.


```
Allowed commands: help, quit, pack
Allowed action for command <pack>: list, find, send, receive
Command pattern for help, quit: <command>
Command pattern for pack: <command> <action> <param1> <param2>
Example: pack send PackName PackSize
         pack receive PackNumber ReceiveCode
         pack list
         pack find PackNumber
```

