/*******************************************************************************
 * Copyright (c) 2014 Luis M. Gallardo D..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Luis M. Gallardo D. - initial implementation
 ******************************************************************************/
package com.lgallardo.qbittorrentclient;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class TorrentDetailsFragment extends Fragment {

	// Torrent variables
	String name, info, hash, ratio, size, progress, state, leechs, seeds, priority, savePath, creationDate, comment, totalWasted, totalUploaded,
			totalDownloaded, timeElapsed, nbConnections, shareRatio, uploadRateLimit, downloadRateLimit, downloaded, eta, downloadSpeed, uploadSpeed,
			percentage = "";

	String hostname;
	String protocol;
	int port;
	String username;
	String password;
	String url;

	int position;

	JSONObject json2;

	private AdView adView;
	private View rootView;

	public TorrentDetailsFragment() {
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return this.position;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Restore last position from savedInstanceState
		if (savedInstanceState != null) {
			// Restore last state for checked position.
			position = savedInstanceState.getInt("itemPosition", 0);
		}

		// Tell the host activity that your fragment has menu options that it
		// wants to add/replace/delete using the onCreateOptionsMenu method.
		setHasOptionsMenu(true);

		rootView = inflater.inflate(R.layout.torrent_details, container, false);

		savePath = "";
		creationDate = "";
		comment = "";
		uploadRateLimit = "";
		downloadRateLimit = "";
		totalWasted = "";
		totalUploaded = "";
		totalDownloaded = "";
		timeElapsed = "";
		nbConnections = "";
		shareRatio = "";

		try {

			if (savedInstanceState != null) {

				// Get saved values

				name = savedInstanceState.getString("torrentDetailName", "");
				size = savedInstanceState.getString("torrentDetailSize", "");
				hash = savedInstanceState.getString("torrentDetailHash", "");
				ratio = savedInstanceState.getString("torrentDetailRatio", "");
				state = savedInstanceState.getString("torrentDetailState", "");
				leechs = savedInstanceState.getString("torrentDetailLeechs", "");
				seeds = savedInstanceState.getString("torrentDetailSeeds", "");
				progress = savedInstanceState.getString("torrentDetailProgress", "");
				priority = savedInstanceState.getString("torrentDetailPriority", "");
				eta = savedInstanceState.getString("torrentDetailEta", "");
				uploadSpeed = savedInstanceState.getString("torrentDetailUploadSpeed", "");
				downloadSpeed = savedInstanceState.getString("torrentDetailDownloadSpeed", "");
				downloaded = savedInstanceState.getString("torrentDetailDownloaded", "");

			} else {

				name = MainActivity.lines[position].getFile();
				size = MainActivity.lines[position].getSize();
				hash = MainActivity.lines[position].getHash();
				ratio = MainActivity.lines[position].getRatio();
				progress = MainActivity.lines[position].getProgress();
				state = MainActivity.lines[position].getState();
				leechs = MainActivity.lines[position].getLeechs();
				seeds = MainActivity.lines[position].getSeeds();
				hash = MainActivity.lines[position].getHash();
				priority = MainActivity.lines[position].getPriority();
				eta = MainActivity.lines[position].getEta();
				uploadSpeed = MainActivity.lines[position].getUploadSpeed();
				downloadSpeed = MainActivity.lines[position].getDownloadSpeed();
				downloaded = MainActivity.lines[position].getDownloaded();
			}

			TextView nameTextView = (TextView) rootView.findViewById(R.id.torrentName);
			TextView sizeTextView = (TextView) rootView.findViewById(R.id.torrentSize);
			TextView ratioTextView = (TextView) rootView.findViewById(R.id.torrentRatio);
			TextView progressTextView = (TextView) rootView.findViewById(R.id.torrentProgress);
			TextView stateTextView = (TextView) rootView.findViewById(R.id.torrentState);
			TextView leechsTextView = (TextView) rootView.findViewById(R.id.torrentLeechs);
			TextView seedsTextView = (TextView) rootView.findViewById(R.id.torrentSeeds);
			TextView hashTextView = (TextView) rootView.findViewById(R.id.torrentHash);
			TextView etaTextView = (TextView) rootView.findViewById(R.id.torrentEta);
			TextView priorityTextView = (TextView) rootView.findViewById(R.id.torrentPriority);
			TextView downloadSpeedTextView = (TextView) rootView.findViewById(R.id.torrentDownloadSpeed);
			TextView uploadSpeedTextView = (TextView) rootView.findViewById(R.id.torrentUploadSpeed);
			TextView pathTextView = (TextView) rootView.findViewById(R.id.torrentSavePath);
			TextView creationDateTextView = (TextView) rootView.findViewById(R.id.torrentCreationDate);
			TextView commentTextView = (TextView) rootView.findViewById(R.id.torrentComment);
			TextView totalWastedTextView = (TextView) rootView.findViewById(R.id.torrentTotalWasted);
			TextView totalUploadedTextView = (TextView) rootView.findViewById(R.id.torrentTotalUploaded);
			TextView totalDownloadedTextView = (TextView) rootView.findViewById(R.id.torrentTotalDownloaded);
			TextView timeElapsedTextView = (TextView) rootView.findViewById(R.id.torrentTimeElapsed);
			TextView nbConnectionsTextView = (TextView) rootView.findViewById(R.id.torrentNbConnections);
			TextView shareRatioTextView = (TextView) rootView.findViewById(R.id.torrentShareRatio);
			TextView uploadRateLimitTextView = (TextView) rootView.findViewById(R.id.torrentUploadRateLimit);
			TextView downloadRateLimitTextView = (TextView) rootView.findViewById(R.id.torrentDownloadRateLimit);

			nameTextView.setText(name);
			sizeTextView.setText(size);
			ratioTextView.setText(ratio);
			stateTextView.setText(state);
			leechsTextView.setText(leechs);
			seedsTextView.setText(seeds);
			progressTextView.setText(progress);
			hashTextView.setText(hash);
			etaTextView.setText(eta);
			priorityTextView.setText(priority);
			downloadSpeedTextView.setText(downloadSpeed);
			uploadSpeedTextView.setText(uploadSpeed);

			pathTextView.setText(savePath);
			creationDateTextView.setText(creationDate);
			commentTextView.setText(comment);
			totalWastedTextView.setText(totalWasted);
			totalUploadedTextView.setText(totalUploaded);
			totalDownloadedTextView.setText(totalDownloaded);
			timeElapsedTextView.setText(timeElapsed);
			nbConnectionsTextView.setText(nbConnections);
			shareRatioTextView.setText(shareRatio);
			uploadRateLimitTextView.setText(uploadRateLimit);
			downloadRateLimitTextView.setText(downloadRateLimit);

			// Set status icon

			nameTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checking, 0, 0, 0);

			if ("pausedUP".equals(state) || "pausedDL".equals(state)) {
				nameTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.paused, 0, 0, 0);
			}

			if ("stalledUP".equals(state)) {
				nameTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stalledup, 0, 0, 0);
			}

			if ("stalledDL".equals(state)) {
				nameTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stalleddl, 0, 0, 0);
			}

			if ("downloading".equals(state)) {
				nameTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.downloading, 0, 0, 0);
			}

			if ("uploading".equals(state)) {
				nameTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.uploading, 0, 0, 0);
			}

			if ("queuedDL".equals(state) || "queuedUP".equals(state)) {
				nameTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.queued, 0, 0, 0);
			}

			// Execute the task in background
			qBittorrentGeneralInfoTask qgit = new qBittorrentGeneralInfoTask();

			qgit.execute(new View[] { rootView });

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("TorrentDetailsFragment - onCreateView", e.toString());
		}

		// Show progressBar
		if (MainActivity.progressBar != null) {
			MainActivity.progressBar.setVisibility(View.VISIBLE);
		}

		// Load banner
		loadBanner();

		// Execute the task in background
		qBittorrentGeneralInfoTask qgit = new qBittorrentGeneralInfoTask();

		qgit.execute(new View[] { rootView });
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("torrentDetailName", name);
		outState.putString("torrentDetailSize", size);
		outState.putString("torrentDetailHash", hash);
		outState.putString("torrentDetailRatio", ratio);
		outState.putString("torrentDetailState", state);
		outState.putString("torrentDetailLeechs", leechs);
		outState.putString("torrentDetailSeeds", seeds);
		outState.putString("torrentDetailProgress", progress);
		outState.putString("torrentDetailPriority", priority);
		outState.putString("torrentDetailEta", eta);
		outState.putString("torrentDetailUploadSpeed", uploadSpeed);
		outState.putString("torrentDetailDownloadSpeed", downloadSpeed);
		outState.putString("torrentDetailDownloaded", downloaded);

	}

	// @Override
	public void onListItemClick(ListView parent, View v, int position, long id) {

	}

	// @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (menu != null) {

			if (getActivity().findViewById(R.id.one_frame) != null) {
				menu.findItem(R.id.action_refresh).setVisible(false);
			}

			menu.findItem(R.id.action_search).setVisible(false);
			menu.findItem(R.id.action_resume_all).setVisible(false);
			menu.findItem(R.id.action_pause_all).setVisible(false);
			menu.findItem(R.id.action_add).setVisible(false);

			menu.findItem(R.id.action_resume).setVisible(true);
			menu.findItem(R.id.action_pause).setVisible(true);
			menu.findItem(R.id.action_increase_prio).setVisible(true);
			menu.findItem(R.id.action_decrease_prio).setVisible(true);
			menu.findItem(R.id.action_delete).setVisible(true);
			menu.findItem(R.id.action_delete_drive).setVisible(true);
			menu.findItem(R.id.action_download_rate_limit).setVisible(true);
			menu.findItem(R.id.action_upload_rate_limit).setVisible(true);

		}
	}

	// Load Banner
	public void loadBanner() {

		// Get the adView.
		adView = (AdView) getActivity().findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder().build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);

	}

	// Here is where the action happens
	private class qBittorrentGeneralInfoTask extends AsyncTask<View, View, View[]> {

		protected View[] doInBackground(View... rootViews) {
			// Get torrent's extra info
			url = "json/propertiesGeneral/";

			try {

				JSONParser jParser = new JSONParser(MainActivity.hostname, MainActivity.subfolder, MainActivity.protocol, MainActivity.port,
						MainActivity.username, MainActivity.password, MainActivity.connection_timeout, MainActivity.data_timeout);

				json2 = jParser.getJSONFromUrl(url + hash);

				MainActivity.lines[position].setSavePath(json2.getString(MainActivity.TAG_SAVE_PATH));
				MainActivity.lines[position].setCreationDate(json2.getString(MainActivity.TAG_CREATION_DATE));
				MainActivity.lines[position].setComment(json2.getString(MainActivity.TAG_COMMENT));
				MainActivity.lines[position].setTotalWasted(json2.getString(MainActivity.TAG_TOTAL_WASTED));
				MainActivity.lines[position].setTotalUploaded(json2.getString(MainActivity.TAG_TOTAL_UPLOADED));
				MainActivity.lines[position].setTotalDownloaded(json2.getString(MainActivity.TAG_TOTAL_DOWNLOADED));
				MainActivity.lines[position].setTimeElapsed(json2.getString(MainActivity.TAG_TIME_ELAPSED));
				MainActivity.lines[position].setNbConnections(json2.getString(MainActivity.TAG_NB_CONNECTIONS));
				MainActivity.lines[position].setShareRatio(json2.getString(MainActivity.TAG_SHARE_RATIO));
				MainActivity.lines[position].setUploadLimit(json2.getString(MainActivity.TAG_UPLOAD_LIMIT));
				MainActivity.lines[position].setDownloadLimit(json2.getString(MainActivity.TAG_DOWNLOAD_LIMIT));

			} catch (Exception e) {

				// MainActivity.lines[position].setSavePath(" ");
				// MainActivity.lines[position].setCreationDate(" ");
				// MainActivity.lines[position].setComment(" ");
				// MainActivity.lines[position].setTotalWasted(" ");
				// MainActivity.lines[position].setTotalUploaded(" ");
				// MainActivity.lines[position].setTotalDownloaded(" ");
				// MainActivity.lines[position].setTimeElapsed(" ");
				// MainActivity.lines[position].setNbConnections(" ");
				// MainActivity.lines[position].setShareRatio(" ");
				// MainActivity.lines[position].setUploadLimit(" ");
				// MainActivity.lines[position].setDownloadLimit(" ");

				Log.e("TorrentFragment:", e.toString());

			}

			return rootViews;

		}

		@Override
		protected void onPostExecute(View[] rootViews) {

			View rootView = rootViews[0];

			TextView pathTextView, creationDateTextView, commentTextView, uploadRateLimitTextView, downloadRateLimitTextView, totalWastedTextView, totalUploadedTextView, totalDownloadedTextView, timeElapsedTextView, nbConnectionsTextView, shareRatioTextView = null;

			pathTextView = (TextView) rootView.findViewById(R.id.torrentSavePath);
			creationDateTextView = (TextView) rootView.findViewById(R.id.torrentCreationDate);
			commentTextView = (TextView) rootView.findViewById(R.id.torrentComment);
			uploadRateLimitTextView = (TextView) rootView.findViewById(R.id.torrentUploadRateLimit);
			downloadRateLimitTextView = (TextView) rootView.findViewById(R.id.torrentDownloadRateLimit);
			totalWastedTextView = (TextView) rootView.findViewById(R.id.torrentTotalWasted);
			totalUploadedTextView = (TextView) rootView.findViewById(R.id.torrentTotalUploaded);
			totalDownloadedTextView = (TextView) rootView.findViewById(R.id.torrentTotalDownloaded);
			timeElapsedTextView = (TextView) rootView.findViewById(R.id.torrentTimeElapsed);
			nbConnectionsTextView = (TextView) rootView.findViewById(R.id.torrentNbConnections);
			shareRatioTextView = (TextView) rootView.findViewById(R.id.torrentShareRatio);

			savePath = MainActivity.lines[position].getSavePath();
			creationDate = MainActivity.lines[position].getCreationDate();
			comment = MainActivity.lines[position].getComment();
			uploadRateLimit = MainActivity.lines[position].getUploadLimit();
			downloadRateLimit = MainActivity.lines[position].getDownloadLimit();
			totalWasted = MainActivity.lines[position].getTotalWasted();
			totalUploaded = MainActivity.lines[position].getTotalUploaded();
			totalDownloaded = MainActivity.lines[position].getTotalDownloaded();
			timeElapsed = MainActivity.lines[position].getTimeElapsed();
			nbConnections = MainActivity.lines[position].getNbConnections();
			shareRatio = MainActivity.lines[position].getShareRatio();

			pathTextView.setText(savePath);
			creationDateTextView.setText(creationDate);
			commentTextView.setText(comment);
			uploadRateLimitTextView.setText(uploadRateLimit);
			downloadRateLimitTextView.setText(downloadRateLimit);
			totalWastedTextView.setText(totalWasted);
			totalUploadedTextView.setText(totalUploaded);
			totalDownloadedTextView.setText(totalDownloaded);
			timeElapsedTextView.setText(timeElapsed);
			nbConnectionsTextView.setText(nbConnections);
			shareRatioTextView.setText(shareRatio);

			try {
				if (json2 == null) {
					Toast.makeText(getActivity(), R.string.torrent_details_cant_general_info, Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}

			// Hide progressBar
			if (MainActivity.progressBar != null) {
				MainActivity.progressBar.setVisibility(View.INVISIBLE);
			}

		}

	}

}
