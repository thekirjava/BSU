from bottle import run, template, base64, get
import os


def check_token(token):
    return token == 'admin'


@get('/task=<task>&input=<input>&token=<token>')
def task(task, input, token):
    if not check_token(token):
        return 'Invalid token!'
    if task == '2':
        bit_stuffing_coder = BitStuffingEncoder()
        data = str(base64.b64decode(input))
        encode_data = bit_stuffing_coder.encode(data)
        decode_data = bit_stuffing_coder.decode(encode_data)
        return 'Query data: {data}<br>Encode data: {encode_data}<br>Decode data: {decode_data}'.format(
            data=input, encode_data=encode_data, decode_data=decode_data)
    elif task == '3':
        list_coder = ListCoder()
        data = str(base64.b64decode(input))
        encode_data = list_coder.encode(data)
        decode_data = list_coder.decode(encode_data)
        return 'Query data: {data}<br>Encode data: {encode_data}<br>Decode data: {decode_data}'.format(
            data=input, encode_data=encode_data, decode_data=decode_data)
    return template('<b>No such task "{{task}}"</b>!', task=task)


class BitStuffingEncoder:
    def __init__(self, length=6):
        self.__length = length

    def encode(self, data):
        res = ''
        count = 0
        for bit in data:
            count = count + 1 if bit == '1' else 0
            res += bit
            if count == self.__length:
                res += '0'
                count = 0
        return res

    def decode(self, data):
        res = ''
        count = 0
        for bit in data:
            if count == self.__length:
                count = 0
                continue
            count = count + 1 if bit == '1' else 0
            res += bit
        return res


class ListCoder:
    def encode(self, data):
        res = bin(ord('['))
        res += bin(data[0][0])
        res += bin(data[0][1])
        res += bin(data[1])
        if data[2] is list:
            res += self.encode(data[2])
        res += bin(ord(']'))
        return res

    def decode(self, data):
        t = [0, 0]
        for i in range(8, 24):
            buf = t[0] if i < 8 else t[1]
            buf <<= 1
            buf |= int(data[i])
            if i < 8:
                t[0] = chr(buf)
            else:
                t[1] = chr(buf)
        sz = 0
        for i in range(24, 40):
            sz <<= 1
            sz |= data[i]
        bit_counter = 0
        res = ""
        buf = 0
        for i in range(40, 40 + sz):
            buf <<= 1
            buf |= int(data[i])
            bit_counter += 1
            if bit_counter == 8:
                bit_counter = 0
                if buf == '[':
                    res = self.decode(buf[i:])
                    break
                res += chr(buf)
        return [str.join(t), sz, res]


run(host="0.0.0.0", port=int(os.environ.get("PORT", 5000)))
