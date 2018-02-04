/*
    This file is part of lib-rANS.
    Copyright 2018 Harshad RJ

    lib-rANS is free software: you can redistribute it and/or modify
    it under the terms of the Lesser GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    lib-rANS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the Lesser GNU General Public License
    along with lib-rANS.  If not, see <http://www.gnu.org/licenses/>.
*/

package hrj.libans;

import java.io.IOException;
import java.io.InputStream;

public class rANSBaseDecoder {

  public rANSBaseDecoder(final InputStream in) throws IOException {
    super();
    this.in = in;
    init();
  }

  private final int RANS_BYTE_L = 1 << 23;

  private final InputStream in;

  private int getNext() throws IOException {
    return in.read();
  }

  private int state;

  private void init() throws IOException {
    final int n4 = getNext();
    final int n3 = getNext();
    final int n2 = getNext();
    final int n1 = getNext();
    state = n1 | (n2 << 8) | (n3 << 16) | (n4 << 24);
  }

  public int getCumFreq(final int scaleBits) {
    return state & ((1 << scaleBits) - 1);
  }

  public void advance(int scaleBits, int freq, int start) throws IOException {
    // s, x = D(x)
    int x = state;
    final int mask = (1 << scaleBits) - 1;
    x = freq * (x >>> scaleBits) + (x & mask) - start;

    // renormalize
    while (Integer.compareUnsigned(x, RANS_BYTE_L) < 0) {
      final int n = getNext();
      assert (n >= 0);
      x = (x << 8) | n;
    }

    state = x;
  }
}
