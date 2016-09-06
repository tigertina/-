import java.util.;
public class Permutation{

	private ArrayListString res = new ArrayListString();

	public ArrayListString Permutation(String str) {
        if(null == str  str.length() == 0)
            return res;
       
		char[] ch = str.toCharArray();
		PermutationSub(ch, 0, ch.length);
        Collections.sort(res);
		return res;
	}

	public void PermutationSub(char[] ch, int t, int n) {
		if (t == n) {
			res.add(new String(ch));
			return;
		}
		for (int i = t; i  n; i++) {
            if(i != t && ch[i] == ch[t])
                continue;
			swap(i, t, ch);
			PermutationSub(ch, t + 1, n);
			swap(i, t, ch);
		}
	}

	public void swap(int x, int y, char[] num) {
        if(x == y)
            return;
		char ch = num[y];
		num[y] = num[x];
		num[x] = ch;
	}

}