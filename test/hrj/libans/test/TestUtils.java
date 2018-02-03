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

package hrj.libans.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

import hrj.libans.InMemoryEncoder;
import hrj.libans.SymbolStatistics;
import hrj.libans.rANSDecoder;

public class TestUtils {

  static void testCodec(final SymbolStatistics encStats, final SymbolStatistics decStats, final int n, final int bound, long seed) throws IOException {
    assert (bound <= (1 << 16));

    final InMemoryEncoder encoder = new InMemoryEncoder(encStats);
    final Random rand = new Random(seed);
    final int[] symbHistory = new int[n];
    for (int i = 0; i < n; i++) {
      int symb = rand.nextInt(bound);
      symbHistory[i]= symb;
      encoder.append(symb);
    }

    final byte[] codedBytes = encoder.finish();
    // System.out.println("Coded bytes: " + codedBytes.length + " input symbols: " + n);
    // System.out.println("  " + Arrays.toString(codedBytes));

    final rANSDecoder decoder = new rANSDecoder(new ByteArrayInputStream(codedBytes), decStats);
    for (int i = 0; i < n; i++) {
      final int expected = symbHistory[i];
      final int symb = decoder.advance();
      assertEquals(expected, symb);
    }

    // System.out.println("---- Done -----\n\n");
  }

  static int requiredBits(int n) {
    return Integer.numberOfTrailingZeros(Integer.highestOneBit(n)) + 1;
  }
}
