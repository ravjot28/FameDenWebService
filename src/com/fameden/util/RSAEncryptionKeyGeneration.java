package com.fameden.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * @author JavaDigest
 * 
 */
public class RSAEncryptionKeyGeneration {
	public static final String ALGORITHM = "RSA";

	public static final String PRIVATE_KEY_FILE = "private.key";

	public static final String PUBLIC_KEY_FILE = "public.key";

	public static void generateKey() {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(ALGORITHM);
			keyGen.initialize(1024);
			final KeyPair key = keyGen.generateKeyPair();

			File privateKeyFile = new File(PRIVATE_KEY_FILE);
			File publicKeyFile = new File(PUBLIC_KEY_FILE);

			if (privateKeyFile.getParentFile() != null) {
				privateKeyFile.getParentFile().mkdirs();
			}
			privateKeyFile.createNewFile();

			if (publicKeyFile.getParentFile() != null) {
				publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();

			ObjectOutputStream publicKeyOS = new ObjectOutputStream(
					new FileOutputStream(publicKeyFile));
			publicKeyOS.writeObject(key.getPublic());
			publicKeyOS.close();

			ObjectOutputStream privateKeyOS = new ObjectOutputStream(
					new FileOutputStream(privateKeyFile));
			privateKeyOS.writeObject(key.getPrivate());
			privateKeyOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean areKeysPresent() {

		File privateKey = new File(PRIVATE_KEY_FILE);
		File publicKey = new File(PUBLIC_KEY_FILE);

		if (privateKey.exists() && publicKey.exists()) {
			return true;
		}
		return false;
	}

	public static byte[] encrypt(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	public static String decrypt(byte[] text, PrivateKey key) {
		byte[] dectyptedText = null;
		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new String(dectyptedText);
	}

	public static void main(String[] args) {

		try {
			if (!areKeysPresent()) {
				generateKey();
			}

			final String originalText = "Text to be encrypted ";
			ObjectInputStream inputStream = null;
			inputStream = new ObjectInputStream(new FileInputStream(
					PUBLIC_KEY_FILE));
			final PublicKey publicKey = (PublicKey) inputStream.readObject();
			final byte[] cipherText = encrypt(originalText, publicKey);
			inputStream = new ObjectInputStream(new FileInputStream(
					PRIVATE_KEY_FILE));
			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			final String plainText = decrypt(cipherText, privateKey);
			System.out.println("Original Text: " + originalText);
			System.out.println("Encrypted Text: " + cipherText.toString());
			System.out.println("Decrypted Text: " + plainText);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}