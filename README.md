# lib ANS

A pure Java implementation of
[ANS](https://en.wikipedia.org/wiki/Asymmetric_Numeral_Systems). Currently,
only the rANS variant has been implemented.

ANS has time performance similar to Huffman coding, but its compression is as
efficient as Arithmetic coding. However, ANS requires more memory (O(N)) as
symbols need to be buffered and encoded in reverse order of their appearance.
The encoded stream also needs to be reversed, requiring temporary storage, but
that should not take up a lot of space (due to compression).

# Status
The code has been well tested (see test/), but consider it beta quality as of now.

# License

LGPL3 (See LICENSE.txt)

# Credits

* Jarek Duda who invented ANS
* Code inspiration from https://github.com/rygorous/ryg_rans
