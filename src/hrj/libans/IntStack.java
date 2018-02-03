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

final public class IntStack {

  // TODO Make it dynamically sized
  private static final int MAX_SIZE = 8 * 1024 * 1024;
  final private int[] store = new int[MAX_SIZE];
  private int count = 0;

  public void push(int value) {
    if (count >= MAX_SIZE) {
      throw new RuntimeException("Exceeded capacity");
    } else {
      store[count++] = value;
    }
  }

  public int pop() {
    if (count <= 0) {
      throw new RuntimeException("Underflow");
    } else {
      return store[--count];
    }

  }

  public int getCount() {
    return count;
  }

  /** O(N) implementation. Useful for testing */
  public int dequeue() {
    if (count <= 0) {
      throw new RuntimeException("Underflow");
    } else {
      int result = store[0];
      for (int i = 0; i < count - 1; i++) {
        store[i] = store[i + 1];
      }
      return result;
    }
  }
}
