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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class InMemoryEncoder {
  public InMemoryEncoder(SymbolStatistics encStats) {
    out = new ByteArrayOutputStream();
    encoder = new rANSEncoder(out, encStats);
  }

  final private ByteArrayOutputStream out;
  final private rANSEncoder encoder;

  public void append(int symb) {
    encoder.append(symb);
  }

  public byte[] finish() throws IOException {
    encoder.finish();
    final byte[] codedBytes = out.toByteArray();
    reverse(codedBytes);
    return codedBytes;
  }

  private void reverse(final byte[] bytes) {
    final int N = bytes.length;
    final int NBy2 = N / 2;
    for (int i = 0; i < NBy2; i++) {
      byte tmp = bytes[i];
      bytes[i] = bytes[N - 1 - i];
      bytes[N - 1 - i] = tmp;
    }
  }
}
