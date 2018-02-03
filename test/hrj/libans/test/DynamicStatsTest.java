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

import java.io.IOException;

import org.junit.jupiter.api.Test;

import hrj.libans.SymbolInfo;
import hrj.libans.SymbolStatistics;

class DynamicStatsTest {
  static final class DynamicStats implements SymbolStatistics {
    private final int[] counts;
    private int totalCount;
    private final int N;

    public DynamicStats() {
      this.N = 256;
      this.counts = new int[N];
      initCounts();
      totalCount = N;
    }

    public DynamicStats(final int N) {
      this.N = N;
      this.counts = new int[N];
      initCounts();
      totalCount = N;
    }

    private void initCounts() {
      for (int i = 0; i < N; i++) {
        counts[i] = 1;
      }
    }

    @Override
    public SymbolInfo findSymbol(final int cumFreq) {
      int sum = 0;
      int prevSum = 0;
      for (int i = 0; i < N; i++) {
        prevSum = sum;
        sum += counts[i];
        // if ((sum + counts[i+1]) > cumFreq) {
        if (sum > cumFreq) {
          final int symb = i;
          return new SymbolInfo(counts[symb], prevSum, symb);
        }
      }
      return new SymbolInfo(counts[N-1], prevSum, N-1);
    }

    @Override
    public int getScaleBits() {
      return TestUtils.requiredBits(totalCount);
    }

    @Override
    public SymbolInfo update(final int s) {
      assert(s < N);
      int sum = 0;
      for (int i = 0; i < s; i++) {
        sum += counts[i];
      }
      counts[s] += 1;
      totalCount++;
      return new SymbolInfo(counts[s] - 1, sum, s);
    }

    @Override
    public void finish() {
    }

  }

  @Test
  void testFew() throws IOException {
    for (int i = 1; i < 100; i += 2) {
      // int bound = 256 * (i % 3 + 1);
      int bound = 256;
      TestUtils.testCodec(new DynamicStats(bound), new DynamicStats(bound), i, bound, i);
    }
  }

  @Test
  void testThousand() throws IOException {
    TestUtils.testCodec(new DynamicStats(), new DynamicStats(), 1000, 256, 13);
  }

  @Test
  void testTenThousand() throws IOException {
    int bound = 512;
    TestUtils.testCodec(new DynamicStats(bound), new DynamicStats(bound), 10000, 512, 13);
  }

  @Test
  void testHundredThousand() throws IOException {
    TestUtils.testCodec(new DynamicStats(), new DynamicStats(), 100*1000, 256, 131);
  }

  @Test
  void testMillion() throws IOException {
    TestUtils.testCodec(new DynamicStats(), new DynamicStats(), 1000*1000, 256, 121);
  }

}
