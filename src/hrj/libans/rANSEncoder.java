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
import java.io.OutputStream;

public class rANSEncoder {

  private int RANS_BYTE_L = 1 << 23;
  private int state = RANS_BYTE_L;

  public rANSEncoder(OutputStream out, SymbolStatistics stats) {
    super();
    this.out = out;
    this.stats = stats;
  }

  private final OutputStream out;

  private int ransEncRenorm(int freq, int scale_bits) throws IOException {
    assert (freq != 0);
    int x = state;
    final int x_max = ((RANS_BYTE_L >>> scale_bits) << 8) * freq;

    while (Integer.compareUnsigned(x, x_max) >= 0) {
      out.write(x); // OutputStream.write() only writes the Least Significant 8 bits
      x >>>= 8;
    }
    return x;
  }

  private IntStack startHistory = new IntStack();
  private IntStack freqHistory = new IntStack();
  private IntStack scaleBitsHistory = new IntStack();

  private final SymbolStatistics stats;

  public void append(int c) {
    scaleBitsHistory.push(stats.getScaleBits());
    SymbolInfo symbInfo = stats.update(c);
    startHistory.push(symbInfo.start);
    freqHistory.push(symbInfo.freq);
  }

  public void finish() throws IOException {
    final int count = startHistory.getCount();
    for (int i = 0; i < count; i++) {
      // x = C(s,x)
      int freq = freqHistory.pop();
      int start = startHistory.pop();
      int scaleBits = scaleBitsHistory.pop();
      int x = ransEncRenorm(freq, scaleBits);
      state = (Integer.divideUnsigned(x, freq) << scaleBits) + (Integer.remainderUnsigned(x, freq)) + start;
    }

    flushState();
    stats.finish();
  }

  private void flushState() throws IOException {
    out.write(state);
    out.write(state >>> 8);
    out.write(state >>> 16);
    out.write(state >>> 24);
    out.close();
  }
}